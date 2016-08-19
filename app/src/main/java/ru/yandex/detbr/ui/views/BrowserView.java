package ru.yandex.detbr.ui.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by shmakova on 19.08.16.
 */

public interface BrowserView {
    void updateUrl(@Nullable String title, @NonNull String url);

    void showProgressBar();

    void hideProgressBar();

    void updateProgressBar(int progress);
}
