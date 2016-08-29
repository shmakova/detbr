package ru.yandex.detbr.ui.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by shmakova on 19.08.16.
 */

public interface BrowserView extends MvpView {

    void close();

    interface UrlListener {
        void onUrl(String url);
    }

    void setOnUrlListener(UrlListener listener);

    void setWebViewCallbacks(WebViewClient client);

    void showSearchText(@Nullable String title, @NonNull String url);

    void showProgress();

    void hideProgress();

    void resetLike();

    void loadPageByUrl(String url);

    void showError();
}
