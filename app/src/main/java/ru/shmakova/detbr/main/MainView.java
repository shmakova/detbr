package ru.shmakova.detbr.main;

import android.support.annotation.ColorRes;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface MainView extends MvpView {
    void showNavigationBars();

    void hideNavigationBars();

    void hideSearchView();

    void showSearchView();

    void changeBackgroundColor(@ColorRes int color);

    void selectTabAtPosition(int position);
}
