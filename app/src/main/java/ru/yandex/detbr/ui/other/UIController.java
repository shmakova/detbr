package ru.yandex.detbr.ui.other;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by shmakova on 18.08.16.
 */

public interface UIController {
    void updateUrl(@Nullable String title, @NonNull String url);

    void showProgressBar();

    void hideProgressBar();

    void updateProgressBar(int progress);
}
