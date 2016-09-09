package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.presentation.views.FavoritesView;
import rx.Observable;

/**
 * Created by shmakova on 28.08.16.
 */

public class FavoritesPresenter extends BaseRxPresenter<FavoritesView, List<Card>> {
    @NonNull
    private final DataRepository dataRepository;
    @NonNull
    private final NavigationManager navigationManager;

    public FavoritesPresenter(@NonNull DataRepository dataRepository,
                              @NonNull NavigationManager navigationManager) {
        this.dataRepository = dataRepository;
        this.navigationManager = navigationManager;
    }

    public void loadCards(boolean pullToRefresh) {
        Observable<List<Card>> observable = dataRepository.getFavouriteCards();
        subscribe(observable, pullToRefresh);
    }

    protected void onNext(List<Card> data) {
        if (isViewAttached()) {
            if (data.isEmpty()) {
                getView().showEmptyView();
            } else {
                getView().setData(data);
            }
        }
    }

    public void onFindInterestingButtonClick() {
        navigationManager.openCards();
        navigationManager.selectTabAtPosition(0);
    }
}
