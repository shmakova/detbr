package ru.yandex.detbr.ui.presenters;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.net.URI;
import java.net.URISyntaxException;

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
    private final WebViewClient client;

    private interface UrlCheckListener {
        void urlChecked(boolean isGood);
    }

    public BrowserPresenter(@NonNull WotService wotService) {
        this.wotService = wotService;
        this.client = new BrowserWebViewClient();
    }

    @Override
    public void attachView(BrowserView view) {
        super.attachView(view);
        view.setOnUrlListener(this::loadUrl);
        view.setWebViewCallbacks(client);
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
                getView().resetLike();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            loadUrl(url);
            return true;
        }
    }
}
