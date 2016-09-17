package ru.yandex.detbr.presentation.presenters;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.presentation.views.MainView;
import ru.yandex.detbr.utils.UrlUtils;

/**
 * Created by shmakova on 29.08.16.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {
    private final static String FIRST_START = "FIRST_START";

    @NonNull
    private final NavigationManager navigationManager;
    @NonNull
    private final SharedPreferences sharedPreferences;


    public MainPresenter(@NonNull NavigationManager navigationManager,
                         @NonNull SharedPreferences sharedPreferences
    ) {
        this.navigationManager = navigationManager;
        this.sharedPreferences = sharedPreferences;
    }

    public void onFirstLoad(int tabId) {
        if (tabId == -1) {
            openCards();
            boolean isFirstStart = sharedPreferences.getBoolean(FIRST_START, true);

            if (isFirstStart) {
                openIntro();
            } else {
                openCards();
            }
        } else {
            if (tabId == NavigationManager.TABS_TAB_POSITION) {
                openTabs();

                if (isViewAttached()) {
                    getView().selectTabAtPosition(NavigationManager.TABS_TAB_POSITION);
                }
            }
        }
    }

    private void openIntro() {
        if (isViewAttached()) {
            navigationManager.openIntro();
            getView().hideNavigationBars();
        }
    }

    private void openCards() {
        if (isViewAttached()) {
            navigationManager.openCards();
            getView().showNavigationBars();
            getView().changeBackgroundColor(R.color.light_background);
        }
    }

    private void openFavorites() {
        if (isViewAttached()) {
            navigationManager.openFavorites();
            getView().showSearchView();
            getView().changeBackgroundColor(R.color.light_background);
        }
    }

    private void openTabs() {
        if (isViewAttached()) {
            navigationManager.openTabs();
            getView().hideSearchView();
            getView().changeBackgroundColor(R.color.dark_background);
        }
    }

    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_cards:
                openCards();
                return;
            case R.id.tab_favorites:
                openFavorites();
                return;
            case R.id.tab_tabs:
                openTabs();
                return;
            default:
        }
    }


    public void onSearchAction(String currentQuery) {
        navigationManager.openBrowser(currentQuery);
    }

    public void onCardsItemClick(Card card) {
        if (card.type().equals(Card.YOUTUBE_TYPE)) {
            navigationManager.openYoutubePlayer(UrlUtils.extractVideoIdFromUrl(card.url()));
        } else {
            navigationManager.openBrowser(card.url());
        }
    }

    public void onBackPressed() {
        if (isViewAttached()) {
            getView().showNavigationBars();
        }
    }

    public void onStartClick() {
        sharedPreferences
                .edit()
                .putBoolean(FIRST_START, false)
                .apply();

        openCards();
    }
}
