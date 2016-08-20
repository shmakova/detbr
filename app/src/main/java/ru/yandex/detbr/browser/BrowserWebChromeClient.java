package ru.yandex.detbr.browser;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import ru.yandex.detbr.ui.views.BrowserView;

/**
 * Created by shmakova on 18.08.16.
 */

public class BrowserWebChromeClient extends WebChromeClient {
    @NonNull
    private final BrowserView browserView;

    public BrowserWebChromeClient(@NonNull Activity activity) {
        this.browserView = (BrowserView) activity;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        browserView.updateProgressBar(newProgress);
    }
}
