package ru.shmakova.detbr.browser;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.xwalk.core.XWalkGetBitmapCallback;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import ru.shmakova.detbr.base.BasePresenter;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.data.stopwords.StopWordsRepository;
import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.tabs.TabsManager;
import ru.shmakova.detbr.utils.UrlUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BrowserPresenter extends BasePresenter<BrowserView> {
    private static final String CHILD_SAFETY_HTML = "file:///android_asset/child_safety.html";
    private static final String LUCKY_PAGE_HTML = "file:///android_asset/lucky_page.html";

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

    public XWalkResourceClient provideResourceClient(XWalkView xWalkView) {
        return new BrowserResourceClient(xWalkView);
    }

    public XWalkUIClient provideXWalkUIClient(XWalkView xWalkView) {
        return new BrowserWalkUIClient(xWalkView);
    }

    private class BrowserResourceClient extends XWalkResourceClient {
        public BrowserResourceClient(XWalkView view) {
            super(view);
        }

        @Override
        public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
            if (UrlUtils.isHttpLink(url)) {
                if (currentUrl != null && currentUrl.equals(url)) {
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

                view.loadUrl(safeUrl);
                return true;
            }

            return false;
        }


        @Override
        public void onProgressChanged(XWalkView view, int newProgress) {
            Timber.d("onProgressChanged: %s", newProgress);

            if (isViewAttached()) {
                getView().updateProgress(newProgress);
            }
        }
    }

    private class BrowserWalkUIClient extends XWalkUIClient {
        public BrowserWalkUIClient(XWalkView view) {
            super(view);
        }

        @Override
        public void onPageLoadStarted(XWalkView view, String url) {
            Timber.d("onPageLoadStarted: %s", url);

            if (isViewAttached()) {
                getView().showSearchText(view.getTitle(), UrlUtils.getHost(url));
                getView().showProgress();
                getView().hideLike();
            }
        }

        @Override
        public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
            Timber.d("onPageLoadStopped: %s, status: %s", url, status);

            if (isViewAttached()) {
                if (UrlUtils.isHttpLink(url)) {
                    getView().showSearchText(view.getTitle(), UrlUtils.getHost(url));
                    getView().showLike();
                }
                getView().hideProgress();
                getView().showLike(cardsRepository.isUrlLiked(url));
                view.postInvalidate();
            }

            if (status == LoadStatus.FINISHED) {
                view.captureBitmapAsync(new XWalkGetBitmapCallback() {
                    @Override
                    public void onFinishGetBitmap(Bitmap bitmap, int i) {
                        Timber.d("onFinishGetBitmap: %s", bitmap);

                        updateTab(Tab.builder()
                                .preview(bitmap)
                                .url(view.getUrl())
                                .title(view.getTitle())
                                .build());
                    }
                });
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
