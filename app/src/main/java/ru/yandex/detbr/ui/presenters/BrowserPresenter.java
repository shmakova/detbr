package ru.yandex.detbr.ui.presenters;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.net.URI;
import java.net.URISyntaxException;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.wot_network.WotService;
import ru.yandex.detbr.ui.views.BrowserView;
import ru.yandex.detbr.utils.UrlCheckerUtils;
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
    private final DataRepository dataRepository;

    private String currentUrl;
    private boolean isPageInCard;
    private boolean isPageLiked;


    public BrowserPresenter(@NonNull WotService wotService,
                            @NonNull DataRepository dataRepository) {
        this.wotService = wotService;
        this.dataRepository = dataRepository;
    }

    private interface UrlCheckListener {
        void urlChecked(boolean isGood);
    }

    @Override
    public void attachView(BrowserView view) {
        super.attachView(view);
        view.setOnUrlListener(this::loadUrl);
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

            wotService.getLinkReputation(domain)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(wotResponse -> listener.urlChecked(wotResponse.isSafe()));
        } catch (URISyntaxException e) {
            Timber.e(e, "Error while parsing url");
        }
    }

    private boolean getLikeFromUrl(@NonNull String url) {
        return dataRepository.getLikeFromUrl(url);
    }

    private void changeLike(@NonNull String url) {
        dataRepository.changeLike(url);
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
        return new WebChromeClient();
    }

    private class BrowserWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(@NonNull WebView webView, String url) {
            if (isViewAttached()) {
                getView().showSearchText(webView.getTitle(), url);
                getView().hideProgress();
                webView.postInvalidate();
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            if (isViewAttached()) {
                getView().showProgress();
                isPageLiked = isPageInCard = getLikeFromUrl(url);
                getView().setLike(isPageInCard);
                currentUrl = url;
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            loadUrl(url);
            return true;
        }
    }
}
