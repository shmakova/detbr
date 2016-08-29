package ru.yandex.detbr.ui.presenters;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.ui.managers.NavigationManager;
import ru.yandex.detbr.ui.views.MainView;

/**
 * Created by shmakova on 29.08.16.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {
    @NonNull
    private final SharedPreferences sharedPreferences;
    @NonNull
    private final NavigationManager navigationManager;
    @Nullable
    private String school;


    public MainPresenter(@NonNull SharedPreferences sharedPreferences,
                         @NonNull NavigationManager navigationManager) {
        this.sharedPreferences = sharedPreferences;
        this.navigationManager = navigationManager;
    }

    public void onFirstLoad() {
        loadDataFromSharedPreference();

        if (school == null || school.isEmpty()) {
            openSchools();
        } else {
            openCards();
        }
    }

    private void openSchools() {
        if (isViewAttached()) {
            getView().hideNavigationBars();
            getView().showToolbar();
            navigationManager.openSchools();
        }
    }

    private void openCards() {
        if (isViewAttached()) {
            getView().showNavigationBars();
            getView().hideToolbar();
            navigationManager.openCards();
        }
    }

    private void openFavorites() {
        if (isViewAttached()) {
            getView().showNavigationBars();
            getView().hideToolbar();
            navigationManager.openFavorites();
        }
    }

    private void openTabs() {
        if (isViewAttached()) {
            getView().showNavigationBars();
            getView().hideSearchView();
            getView().hideToolbar();
            navigationManager.openTabs();
        }
    }

    private void openCategoryCards(Category category) {
        if (isViewAttached()) {
            getView().updateToolbar(category.getTitle(), true, category.getBackgroundColor());
            getView().hideNavigationBars();
            getView().showToolbar();
            navigationManager.openCategoryCards(category);
        }
    }

    private void loadDataFromSharedPreference() {
        school = sharedPreferences.getString(DataRepository.SCHOOL_TAG, null);
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

    public void onSchoolClick() {
        loadDataFromSharedPreference();
        openCards();
    }

    public void onCategoriesItemClick(Category category) {
        openCategoryCards(category);
    }

    public void onActionMenuItemSelected(@IdRes int id) {
        if (id == R.id.action_voice_rec && isViewAttached()) {
            getView().showSpeechRecognizer();
        }
    }

    public void onSearchAction(String currentQuery) {
        navigationManager.openBrowser(currentQuery);
    }

    public void onCardsItemClick(Card card) {
        navigationManager.openBrowser(card.getUrl());
    }
}
