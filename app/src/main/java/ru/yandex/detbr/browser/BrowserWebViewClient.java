package ru.yandex.detbr.browser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.yandex.detbr.ui.views.BrowserView;

/**
 * Created by shmakova on 18.08.16.
 */

public class BrowserWebViewClient extends WebViewClient {
    @NonNull
    private final BrowserView browserView;

    public BrowserWebViewClient(@NonNull Activity activity) {
        browserView = (BrowserView) activity;
    }

    @Override
    public void onPageFinished(@NonNull WebView view, String url) {
        browserView.updateUrl(view.getTitle(), url);
        browserView.hideProgressBar();
        view.postInvalidate();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        browserView.showProgressBar();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(BrowserUrlUtils.getSafeUrl(url));
        return true;
    }
}
