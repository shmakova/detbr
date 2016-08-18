package ru.yandex.detbr.browser;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import ru.yandex.detbr.ui.other.UIController;
import timber.log.Timber;

/**
 * Created by shmakova on 18.08.16.
 */

public class DetbrWebChromeClient extends WebChromeClient {
    @NonNull
    private final UIController uiController;


    public DetbrWebChromeClient(@NonNull Activity activity) {
        this.uiController = (UIController) activity;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        uiController.updateProgressBar(newProgress);
    }
}
