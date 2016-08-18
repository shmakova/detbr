package ru.yandex.detbr.browser;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.yandex.detbr.ui.other.UIController;

/**
 * Created by shmakova on 18.08.16.
 */

public class DetbrWebViewClient extends WebViewClient {
    private UIController uiController;

    public DetbrWebViewClient(@NonNull Activity activity) {
        uiController = (UIController) activity;
    }

    @Override
    public void onPageFinished(@NonNull WebView view, String url) {
        if (view.isShown()) {
            uiController.updateUrl(view.getTitle(), url);
            view.postInvalidate();
        }
    }
}
