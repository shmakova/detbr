package ru.shmakova.detbr.browser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.hannesdorfmann.mosby.mvp.MvpView;

import rx.Observable;

public interface BrowserView extends MvpView {

    void close();

    void updateProgress(int newProgress);

    void openTabs();

    void showLuckyPage();

    interface LoadUrlListener {
        void onLoadUrl(String url);
    }

    void showSearchText(@Nullable String title, @NonNull String host);

    void showProgress();

    void hideProgress();

    void showLike(boolean like);

    void loadPageByUrl(String url);

    boolean resolveUrl(String url, LoadUrlListener listener);

    void showLike();

    void hideLike();

    Observable<String> loadUrlEvents();

    Observable<Pair<String, String>> likeClicks();
}
