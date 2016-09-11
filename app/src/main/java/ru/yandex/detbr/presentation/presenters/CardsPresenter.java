package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.cards.CardsRepository;
import ru.yandex.detbr.data.categories.Category;
import ru.yandex.detbr.data.schools.SchoolsRepository;
import ru.yandex.detbr.presentation.views.CardsView;
import rx.Observable;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsPresenter extends BaseRxPresenter<CardsView, List<Card>> {
    @NonNull
    private final CardsRepository cardsRepository;
    @NonNull
    private final SchoolsRepository schoolsRepository;
    private Category category;
    private String school;


    public CardsPresenter(
            @NonNull CardsRepository cardsRepository,
            @NonNull SchoolsRepository schoolsRepository) {
        this.cardsRepository = cardsRepository;
        this.schoolsRepository = schoolsRepository;
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

    public void onCategorySelected(Category category) {
        if (category.equals(this.category)) {
            loadCards(false);
            this.category = null;

            if (isViewAttached()) {
                getView().setBackgroundColor(R.color.transparent);
                getView().setDividerColor(R.color.light_grey);
            }
        } else {
            school = schoolsRepository.loadSchool();
            this.category = category;

            if (category.isSchoolCategory() && school == null) {
                showSchoolFragment();
            } else {
                loadCardsByCategory(category, false);
            }

            if (isViewAttached() && category.color() != null) {
                getView().setBackgroundColor(category.color());
                getView().setDividerColor(R.color.dark_transparent_white);
            }
        }
    }

    public void refresh() {
        loadCardsByCategory(category, false);
    }
}
