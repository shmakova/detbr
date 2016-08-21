package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import ru.yandex.detbr.cards.CardsModel;
import ru.yandex.detbr.ui.views.CardsView;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsPresenter extends Presenter<CardsView> {
    @NonNull
    private final CardsModel cardsModel;

    public CardsPresenter(@NonNull CardsModel cardsModel) {
        this.cardsModel = cardsModel;
    }

    public void loadCards() {
        final CardsView view = view();

        if (view != null) {
            view.setCardsData(cardsModel.getCardsList());
        }
    }
}
