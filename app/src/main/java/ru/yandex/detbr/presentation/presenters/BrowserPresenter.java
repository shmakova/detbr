package ru.yandex.detbr.presentation.presenters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.cards.CardsRepository;
import ru.yandex.detbr.data.tabs.Tab;
import ru.yandex.detbr.data.wot_network.WotService;
import ru.yandex.detbr.managers.TabsManager;
import ru.yandex.detbr.presentation.views.BrowserView;
import ru.yandex.detbr.utils.UrlUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
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
    private final CardsRepository cardsRepository;
    private final CompositeSubscription compositeSubscription;
    private String currentUrl;

    public BrowserPresenter(@NonNull WotService wotService,
                            @NonNull TabsManager tabsManager,
                            @NonNull CardsRepository cardsRepository) {

        this.wotService = wotService;
        this.cardsRepository = cardsRepository;
        this.tabsManager = tabsManager;
        compositeSubscription = new CompositeSubscription();
    }

    public void onTabsClicked() {
        getView().openTabs();
    }

    private interface UrlCheckListener {
        void urlChecked(boolean isGood);
    }

    @Override
    public void attachView(BrowserView view) {
        super.attachView(view);
        view.setOnUrlListener(query -> {
            String url = UrlUtils.getUrlFromQuery(query);
            compositeSubscription.add(
                    tabsManager.addTab(Tab.builder().url(url).build())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(putResult -> tabsManager.updateTabs(),
                                    throwable -> Timber.e("Error adding tab"),
                                    () -> Timber.d("Completed adding tab")));
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

        compositeSubscription.add(
                wotService.getLinkReputation(domain)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                wotResponse -> listener.urlChecked(wotResponse.isSafe()),
                                throwable -> Timber.e(throwable.getMessage(),
                                        "wotService.getLinkReputation error")));
    }

    public void onLikeClick(String title, String url) {
        compositeSubscription.add(cardsRepository.getCardByUrl(url)
                .flatMap(card -> {
                    if (card == null) {
                        final Card newCard = Card.builder()
                                .title(title)
                                .url(url)
                                .like(true)
                                .build();

                        return cardsRepository.saveCard(newCard);
                    } else {
                        return cardsRepository
                                .setLike(card, !card.like())
                                .map(putResult -> card.getLikedCard(!card.like()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    if (isViewAttached()) {
                        getView().setLike(it.like());
                    }
                }));
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
                    getView().showSearchText(webView.getTitle(), UrlUtils.getHost(url));
                    getView().showLike();
                }
                getView().hideProgress();
                getView().setLike(cardsRepository.isUrlLiked(url));
                webView.postInvalidate();

                updateTab(Tab.builder()
                        .preview(getSnapshot(webView))
                        .url(webView.getUrl())
                        .title(webView.getTitle())
                        .build());
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            if (isViewAttached()) {
                getView().showSearchText(webView.getTitle(), UrlUtils.getHost(url));
                getView().showProgress();
                getView().hideLike();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (currentUrl != null && currentUrl.equals(url)) {
                view.goBack();
                return true;
            }

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
            currentUrl = safeUrl;
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        private Bitmap getSnapshot(WebView webView) {
            final int width = 320;
            int height = 480;
            float ratio = (float) height / (float) width;
            Bitmap thumbnail = null;

            if (webView.getWidth() > 0 && webView.getHeight() > 0) {
                int webViewHeight = (int) (webView.getWidth() * ratio);
                int shapshotHeight = webViewHeight > webView.getHeight() ? webView.getHeight() : webViewHeight;
                Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), shapshotHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);
                float factor = width / (float) webView.getWidth();
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

        if (!retainInstance && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
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

    private void updateTab(Tab tab) {
        compositeSubscription.add(
                tabsManager
                        .removeLastTab()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object -> {
                                    tabsManager.addTab(tab)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(putResult -> tabsManager.updateTabs(),
                                                    throwable -> Timber.e("Error adding tab"),
                                                    () -> Timber.d("Completed adding tab"));
                                },
                                throwable -> Timber.e("Error removing tab"),
                                () -> Timber.d("Completed removing tab")));
    }
}
