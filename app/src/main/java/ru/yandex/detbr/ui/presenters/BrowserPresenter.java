package ru.yandex.detbr.ui.presenters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.net.URI;
import java.net.URISyntaxException;

import ru.yandex.detbr.data.tabs.models.Tab;
import ru.yandex.detbr.data.wot_network.WotService;
import ru.yandex.detbr.ui.managers.TabsManager;
import ru.yandex.detbr.ui.views.BrowserView;
import ru.yandex.detbr.utils.UrlCheckerUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shmakova on 19.08.16.
 */

public class BrowserPresenter extends MvpBasePresenter<BrowserView> {

    @NonNull
    private final WotService wotService;
    @NonNull
    private final TabsManager tabsManager;
    private Subscription subscription;

    public BrowserPresenter(@NonNull WotService wotService, @NonNull TabsManager tabsManager) {
        this.wotService = wotService;
        this.tabsManager = tabsManager;
    }

    private interface UrlCheckListener {
        void urlChecked(boolean isGood);
    }

    @Override
    public void attachView(BrowserView view) {
        super.attachView(view);
        view.setOnUrlListener(url -> {
            tabsManager.addTab(Tab.builder().url(url).build());
            loadUrl(url);
        });
    }

    public void onHomeClicked() {
        if (isViewAttached()) {
            getView().close();
        }
    }

    private void loadUrl(String query) {
        String safeUrl = UrlCheckerUtils.getSafeUrlFromQuery(query);
        checkUrlBeforeLoad(safeUrl, valid -> {
            if (isViewAttached()) {
                if (valid) {
                    getView().loadPageByUrl(safeUrl);
                } else {
                    getView().showError();
                }
            }
        });
    }

    private void checkUrlBeforeLoad(String url, UrlCheckListener listener) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost() + "/";

            subscription = wotService.getLinkReputation(domain)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(wotResponse -> listener.urlChecked(wotResponse.isSafe()));
        } catch (URISyntaxException e) {
            Timber.e(e, "Error while parsing url");
        }
    }

    public WebViewClient provideWebViewClient() {
        return new BrowserWebViewClient();
    }

    public WebChromeClient provideWebChromeClient() {
        return new BrowserWebChromeClient();
    }

    private class BrowserWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(@NonNull WebView webView, String url) {
            if (isViewAttached()) {
                getView().showSearchText(webView.getTitle(), url);
                getView().hideProgress();
                webView.postInvalidate();
                tabsManager.updateTab(Tab.builder()
                        .preview(getSnapshot(webView))
                        .url(webView.getUrl())
                        .title(webView.getTitle())
                        .build());
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            if (isViewAttached()) {
                getView().showProgress();
                getView().resetLike();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            loadUrl(url);
            return true;
        }

        private Bitmap getSnapshot(WebView webView) {
            final int width = 320;
            int height = 480;
            Picture picture = webView.capturePicture();
            Bitmap thumbnail = null;

            if (picture.getWidth() > 0 && picture.getHeight() > 0) {
                Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(),
                        picture.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                picture.draw(canvas);
                float factor = width / (float) bitmap.getWidth();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, (int) (bitmap.getHeight() * factor), true);
                height = (height > scaledBitmap.getHeight()) ? scaledBitmap.getHeight() : height;
                thumbnail = Bitmap.createBitmap(scaledBitmap, 0, 0, width, height);

                bitmap.recycle();
                scaledBitmap.recycle();
            }

            return thumbnail;
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance && subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private class BrowserWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (isViewAttached()) {
                getView().updateProgress(newProgress);
            }
        }
    }
}
