package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.cards.CardsRepository;
import ru.yandex.detbr.data.categories.Category;
import ru.yandex.detbr.presentation.views.CardsView;
import rx.Observable;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsPresenter extends BaseRxPresenter<CardsView, List<Card>> {
    @NonNull
    private final CardsRepository cardsRepository;
    private Category category;


    public CardsPresenter(@NonNull CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public void loadCards(boolean pullToRefresh) {
        Observable<List<Card>> observable = cardsRepository.getCardsList();
        subscribe(observable, pullToRefresh);
    }

    public void loadCardsByCategory(Category category, boolean pullToRefresh) {
        Observable<List<Card>> observable = cardsRepository.getCardsByCategory(category);
        subscribe(observable, pullToRefresh);
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
            loadCardsByCategory(category, false);
            this.category = category;

            if (isViewAttached() && category.color() != null) {
                getView().setBackgroundColor(category.color());
                getView().setDividerColor(R.color.dark_transparent_white);
            }
        }

    }
}
