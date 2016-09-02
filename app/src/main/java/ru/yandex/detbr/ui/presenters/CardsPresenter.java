package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.ui.views.CardsView;
import rx.Observable;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsPresenter extends BaseRxPresenter<CardsView, List<Card>> {
    @NonNull
    private final DataRepository dataRepository;

    public CardsPresenter(@NonNull DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void loadCards(boolean pullToRefresh) {
        Observable<List<Card>> observable = dataRepository.getCardsListBySchool();
        subscribe(observable, pullToRefresh);
    }

    public void loadCardsByCategory(Category category, boolean pullToRefresh) {
        Observable<List<Card>> observable = dataRepository.getCardsByCategory(category);
        subscribe(observable, pullToRefresh);
    }
}
