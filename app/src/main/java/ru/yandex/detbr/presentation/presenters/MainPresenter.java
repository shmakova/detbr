package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.categories.Category;
import ru.yandex.detbr.data.schools.SchoolsRepository;
import ru.yandex.detbr.managers.LikeManager;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.presentation.views.MainView;

/**
 * Created by shmakova on 29.08.16.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {
    @NonNull
    private final NavigationManager navigationManager;
    @NonNull
    private final LikeManager likeManager;
    @NonNull
    private final SchoolsRepository schoolsRepository;
    @Nullable
    private String school;


    public MainPresenter(@NonNull NavigationManager navigationManager,
                         @NonNull LikeManager likeManager,
                         @NonNull SchoolsRepository schoolsRepository
    ) {
        this.navigationManager = navigationManager;
        this.likeManager = likeManager;
        this.schoolsRepository = schoolsRepository;
    }

    public void onFirstLoad() {
        loadSchoolFromRepository();

        if (school == null || school.isEmpty()) {
            Thread thread = new Thread(() -> {
                navigationManager.openOnBoarding();
            });
            thread.start();
        } else {
            openCards();
        }
    }

    private void openCards() {
        if (isViewAttached()) {
            navigationManager.openCards();
            getView().showNavigationBars();
            getView().hideToolbar();
            getView().changeBackgroundColor(R.color.white);
        }
    }

    private void openFavorites() {
        if (isViewAttached()) {
            navigationManager.openFavorites();
            getView().showNavigationBars();
            getView().hideToolbar();
            getView().changeBackgroundColor(R.color.white);
        }
    }

    private void openTabs() {
        if (isViewAttached()) {
            navigationManager.openTabs();
            getView().showNavigationBars();
            getView().hideSearchView();
            getView().hideToolbar();
            getView().changeBackgroundColor(R.color.dark_background);
        }
    }

    private void openCategoryCards(Category category) {
        if (isViewAttached()) {
            getView().updateToolbar(category.title(), true, category.color());
            getView().hideNavigationBars();
            getView().showToolbar();
            navigationManager.openCategoryCards(category);
        }
    }

    private void loadSchoolFromRepository() {
        school = schoolsRepository.loadSchool();
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
        navigationManager.openBrowser(card.url());
    }

    public void onLikeClick(Card card) {
        likeManager.setLike(card);
    }

    public void onBackPressed() {
        if (isViewAttached()) {
            getView().showNavigationBars();
            getView().hideToolbar();
        }
    }
}
