package ru.shmakova.detbr.presentation.presenters;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import ru.shmakova.detbr.R;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.data.categories.Category;
import ru.shmakova.detbr.data.categories.CategoryClick;
import ru.shmakova.detbr.data.schools.SchoolsRepository;
import ru.shmakova.detbr.presentation.views.CardsView;
import rx.Observable;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsPresenter extends BaseRxPresenter<CardsView, List<Card>> {
    private final static String FIRST_TIME_CARDS_LOAD = "FIRST_TIME_CARDS_LOAD";

    @NonNull
    private final CardsRepository cardsRepository;
    @NonNull
    private final SchoolsRepository schoolsRepository;
    @NonNull
    private final SharedPreferences sharedPreferences;
    private Category category;
    private String school;


    public CardsPresenter(
            @NonNull CardsRepository cardsRepository,
            @NonNull SchoolsRepository schoolsRepository,
            @NonNull SharedPreferences sharedPreferences) {
        this.cardsRepository = cardsRepository;
        this.schoolsRepository = schoolsRepository;
        this.sharedPreferences = sharedPreferences;
    }

    public void loadCards(boolean pullToRefresh) {
        Observable<List<Card>> observable = cardsRepository.getCardsList();
        subscribe(observable, pullToRefresh);
    }

    public void loadCardsByCategory(Category category, boolean pullToRefresh) {
        Observable<List<Card>> observable = cardsRepository.getCardsByCategory(category);
        subscribe(observable, pullToRefresh);
    }

    private void showSchoolFragment() {
        if (isViewAttached()) {
            getView().showSchoolFragment();
        }
    }

    public void onCategorySelected(CategoryClick categoryClick) {
        Category category = categoryClick.category();

        if (category.equals(this.category)) {
            loadCards(false);
            this.category = null;

            if (isViewAttached()) {
                getView().setBackgroundColor(
                        R.color.light_background,
                        R.color.light_grey,
                        categoryClick.x(),
                        categoryClick.y());
            }
        } else {
            school = schoolsRepository.loadSchool();
            this.category = category;

            if (category.isSchoolCategory() && (school == null || school.isEmpty())) {
                showSchoolFragment();
            } else {
                loadCardsByCategory(category, false);
            }

            if (isViewAttached() && category.color() != null) {
                getView().setBackgroundColor(
                        category.color(),
                        R.color.dark_transparent_white,
                        categoryClick.x(),
                        categoryClick.y());
            }
        }
    }

    public void refresh() {
        loadCardsByCategory(category, false);
    }

    public boolean isFirstLoad() {
        boolean isFirstLoad = sharedPreferences.getBoolean(FIRST_TIME_CARDS_LOAD, true);

        if (isFirstLoad) {
            sharedPreferences
                    .edit()
                    .putBoolean(FIRST_TIME_CARDS_LOAD, false)
                    .apply();

            return true;
        }

        return false;
    }
}
