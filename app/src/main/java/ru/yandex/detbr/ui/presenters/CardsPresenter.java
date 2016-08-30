package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import ru.yandex.detbr.cards.CardsModel;
import ru.yandex.detbr.categories.Category;
import ru.yandex.detbr.db.Repository;
import ru.yandex.detbr.ui.views.CardsView;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsPresenter extends Presenter<CardsView> {
    @NonNull
    private final CardsModel cardsModel;

    @NonNull
    private Repository repository;

    public CardsPresenter(@NonNull CardsModel cardsModel, @NonNull Repository repository) {
        this.cardsModel = cardsModel;
        this.repository = repository;
    }

    public void loadCards() {
        final CardsView view = view();

        if (view != null) {
            view.setCardsData(cardsModel.getCardsListBySchool());
        }
    }

    public void loadFavouriteCards() {
        final CardsView view = view();

        if (view != null) {
            view.setCardsData(cardsModel.getFavouriteCards());
        }
    }

    public void loadCardsByCategory(Category category) {
        final CardsView view = view();

        if (view != null) {
            view.setCardsData(cardsModel.getCardsByCategory(category));
        }
    }

    public void changeLike(@NonNull String url) {
        repository.changeLike(url);
    }
}
