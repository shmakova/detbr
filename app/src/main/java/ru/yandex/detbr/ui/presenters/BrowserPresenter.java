package ru.yandex.detbr.ui.presenters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.tabs.models.Tab;
import ru.yandex.detbr.data.wot_network.WotService;
import ru.yandex.detbr.ui.managers.LikeManager;
import ru.yandex.detbr.ui.managers.TabsManager;
import ru.yandex.detbr.ui.views.BrowserView;
import ru.yandex.detbr.utils.UrlUtils;
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
    @NonNull
    private final LikeManager likeManager;
    @NonNull
    private final DataRepository dataRepository;
    private Subscription subscription;

    public BrowserPresenter(@NonNull WotService wotService,
                            @NonNull TabsManager tabsManager,
                            @NonNull DataRepository dataRepository,
                            @NonNull LikeManager likeManager) {

        this.wotService = wotService;
        this.dataRepository = dataRepository;
        this.tabsManager = tabsManager;
        this.likeManager = likeManager;
    }

    private interface UrlCheckListener {
        void urlChecked(boolean isGood);
    }

    @Override
    public void attachView(BrowserView view) {
        super.attachView(view);
        view.setOnUrlListener(query -> {
            String url = UrlUtils.getUrlFromQuery(query);
            tabsManager.addTab(Tab.builder().url(url).build());
            loadUrl(url);
        });
    }

    public void onHomeClicked() {
        if (isViewAttached()) {
            getView().close();
        }
    }

    private void loadUrl(String url) {
        checkUrlBeforeLoad(url, valid -> {
            if (isViewAttached()) {
                if (valid) {
                    getView().loadPageByUrl(url);
                } else {
                    getView().showError();
                }
            }
        });
    }

    private void checkUrlBeforeLoad(String url, UrlCheckListener listener) {
        String domain = UrlUtils.getHost(url) + "/";

        subscription = wotService.getLinkReputation(domain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        wotResponse -> listener.urlChecked(wotResponse.isSafe()),
                        throwable -> Timber.e(throwable.getMessage(), "wotService.getLinkReputation error"));
    }

    public void onLikeClick(String title, String url) {
        boolean isUrlLiked = likeManager.isUrlLiked(url);
        Card card = Card.builder()
                .title(title)
                .url(url)
                .build();

        if (isUrlLiked) {
            isUrlLiked = false;
        } else {
            dataRepository.saveCard(card);
            isUrlLiked = true;
        }

        likeManager.setLike(card);

        if (isViewAttached()) {
            getView().setLike(isUrlLiked);
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
                if (UrlUtils.isHttpLink(url)) {
                    getView().showSearchText(webView.getTitle(), url);
                    getView().showLike();
                }
                getView().hideProgress();
                getView().setLike(likeManager.isUrlLiked(url));
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
                getView().hideLike();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String safeUrl = url;

            if (url.contains(UrlUtils.GOOGLE_URL) &&
                    !url.contains(UrlUtils.GOOGLE_SAFE_PARAMETER) &&
                    url.contains(UrlUtils.GOOGLE_QUERY_PARAMETER)) {
                safeUrl += "&" + UrlUtils.GOOGLE_SAFE_PARAMETER;
            } else if (url.contains(UrlUtils.YANDEX_URL) &&
                    !url.contains(UrlUtils.YANDEX_SAFE_PARAMETER) &&
                    url.contains(UrlUtils.YANDEX_QUERY_PARAMETER)) {
                safeUrl += "&" + UrlUtils.YANDEX_SAFE_PARAMETER;
            }

            loadUrl(safeUrl);
            return true;
        }

        private Bitmap getSnapshot(WebView webView) {
            final int width = 320;
            int height = 480;
            Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            webView.draw(canvas);
            float factor = width / (float) webView.getWidth();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, (int) (bitmap.getHeight() * factor), true);
            height = height > scaledBitmap.getHeight() ? scaledBitmap.getHeight() : height;
            Bitmap thumbnail = Bitmap.createBitmap(scaledBitmap, 0, 0, width, height);
            bitmap.recycle();
            scaledBitmap.recycle();

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
