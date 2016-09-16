package ru.yandex.detbr.presentation.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by shmakova on 19.08.16.
 */

public interface BrowserView extends MvpView {

    void close();

    void updateProgress(int newProgress);

    void openTabs();

    interface UrlListener {
        void onUrl(String url);
    }

    interface LoadUrlListener {
        void onLoadUrl(String url);
    }

    void setOnUrlListener(UrlListener listener);

    void showSearchText(@Nullable String title, @NonNull String host);

    void showProgress();

    void hideProgress();

    void setLike(boolean like);

    void loadPageByUrl(String url);

    boolean resolveUrl(String url, LoadUrlListener listener);

    void showError();

    void showLike();

    void hideLike();
}
