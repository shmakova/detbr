package ru.yandex.detbr.presentation.views;

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

    void selectTabAtPosition(int position);

    void updateToolbar(String title, Boolean isDisplayHomeAsUpEnabled, String color);

    void showSpeechRecognizer();
}
