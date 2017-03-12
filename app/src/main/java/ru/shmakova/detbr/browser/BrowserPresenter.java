package ru.shmakova.detbr.browser;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.shmakova.detbr.base.BasePresenter;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.data.stopwords.StopWordsRepository;
import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.tabs.TabsManager;
import ru.shmakova.detbr.utils.UrlUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BrowserPresenter extends BasePresenter<BrowserView> {
    @NonNull
    private final TabsManager tabsManager;
    @NonNull
    private final StopWordsRepository stopWordsRepository;
    @NonNull
    private final CardsRepository cardsRepository;

    private String currentUrl;

    public BrowserPresenter(@NonNull TabsManager tabsManager,
                            @NonNull CardsRepository cardsRepository,
                            @NonNull StopWordsRepository stopWordsRepository) {
        this.cardsRepository = cardsRepository;
        this.tabsManager = tabsManager;
        this.stopWordsRepository = stopWordsRepository;
    }

    public void onTabsClicked() {
        getView().openTabs();
    }

    @Override
    public void attachView(BrowserView view) {
        super.attachView(view);

        unsubscribeOnUnbindView(
                getView().loadUrlEvents()
                        // stopWordsRepository.isStopWord(query)) getView().showLuckyPage();
                        .map(UrlUtils::getUrlFromQuery)
                        .switchMap(checkedUrl -> tabsManager
                                .addTab(Tab.builder().url(checkedUrl).build())
                                .doOnNext(putResult -> tabsManager.updateTabs())
                                .map(o -> checkedUrl))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(url -> getView().loadPageByUrl(url)),
                getView().likeClicks()
                        .switchMap(titleUrlPair -> cardsRepository
                                .getCardByUrl(titleUrlPair.second)
                                .flatMap(card -> {
                                    if (card == null) {
                                        final Card newCard = Card.builder()
                                                .title(titleUrlPair.first)
                                                .url(titleUrlPair.second)
                                                .like(true)
                                                .build();

                                        return cardsRepository.saveCard(newCard);
                                    } else {
                                        return cardsRepository
                                                .setLike(card, !card.like())
                                                .map(putResult -> card.withLike(!card.like()));
                                    }
                                }))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(it -> {
                            if (isViewAttached()) {
                                getView().showLike(it.like());
                            }
                        })
        );
    }

    public void onHomeClicked() {
        if (isViewAttached()) {
            getView().close();
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
                    getView().showSearchText(webView.getTitle(), UrlUtils.getHost(url));
                    getView().showLike();
                }
                getView().hideProgress();
                getView().showLike(cardsRepository.isUrlLiked(url));
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
        unsubscribeOnUnbindView(
                tabsManager.removeLastTab()
                        .switchMap(o -> tabsManager.addTab(tab))
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
    }
}
