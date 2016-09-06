package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.presentation.views.FavoritesView;
import rx.Observable;

/**
 * Created by shmakova on 28.08.16.
 */

public class FavoritesPresenter extends BaseRxPresenter<FavoritesView, List<Card>> {
    @NonNull
    private final DataRepository dataRepository;

    public FavoritesPresenter(@NonNull DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void loadCards(boolean pullToRefresh) {
        Observable<List<Card>> observable = dataRepository.getFavouriteCards();
        subscribe(observable, pullToRefresh);
    }
}
