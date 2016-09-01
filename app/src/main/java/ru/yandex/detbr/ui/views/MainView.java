package ru.yandex.detbr.ui.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by shmakova on 19.08.16.
 */

public interface MainView extends MvpView {
    void showNavigationBars();

    void hideNavigationBars();

    void hideSearchView();

    void showSearchView();

    void showToolbar();

    void hideToolbar();

    void resetToolbar();

    void updateToolbar(String title, Boolean isDisplayHomeAsUpEnabled, String color);

    void showSpeechRecognizer();
}
