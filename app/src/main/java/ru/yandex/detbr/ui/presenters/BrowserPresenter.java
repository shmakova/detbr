package ru.yandex.detbr.ui.presenters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.tabs.models.Tab;
import ru.yandex.detbr.data.wot_network.WotService;
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
    private final DataRepository dataRepository;
    private Subscription subscription;

    private String currentUrl;
    private boolean isPageInCard;
    private boolean isPageLiked;

    public BrowserPresenter(@NonNull WotService wotService,
                            @NonNull TabsManager tabsManager,
                            @NonNull DataRepository dataRepository) {

        this.wotService = wotService;
        this.dataRepository = dataRepository;
        this.tabsManager = tabsManager;
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

    private boolean getLikeFromUrl(@NonNull String url) {
        return dataRepository.getLikeFromUrl(url);
    }

    private void changeLike(@NonNull String url) {
        dataRepository.changeLike(url);
    }

    public boolean isCardAlreadyExist(@NonNull String url) {
        return dataRepository.isCardAlreadyExist(url);
    }

    private void saveCardToRepository(String title, String url, @Nullable String cover, boolean like) {
        dataRepository.saveCardToRepository(title, url, cover, like);
    }

    public void onLikeClick(String title, String url) {
        if (isPageInCard) {
            changeLike(currentUrl);
        } else {
            saveCardToRepository(title, url, null, true);
            isPageInCard = true;
        }

        isPageLiked = !isPageLiked;

        if (isViewAttached()) {
            getView().setLike(isPageLiked);
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
                isPageInCard = isCardAlreadyExist(url);
                isPageLiked = isPageInCard && getLikeFromUrl(url);
                getView().setLike(isPageLiked);
                currentUrl = url;
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
            Picture picture = webView.capturePicture();
            Bitmap thumbnail = null;

            if (picture.getWidth() > 0 && picture.getHeight() > 0) {
                Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(),
                        picture.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                picture.draw(canvas);
                float factor = width / (float) bitmap.getWidth();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, (int) (bitmap.getHeight() * factor), true);
                height = height > scaledBitmap.getHeight() ? scaledBitmap.getHeight() : height;
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
