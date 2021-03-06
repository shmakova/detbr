package ru.shmakova.detbr.favorites;

import android.support.annotation.NonNull;

import java.util.List;

import ru.shmakova.detbr.base.BaseRxPresenter;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.CardsRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class FavoritesPresenter extends BaseRxPresenter<FavoritesView, List<Card>> {
    @NonNull
    private final CardsRepository cardsRepository;
    private final CompositeSubscription compositeSubscription;

    public FavoritesPresenter(@NonNull CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
        compositeSubscription = new CompositeSubscription();
    }

    public void loadCards(boolean pullToRefresh) {
        Observable<List<Card>> observable = cardsRepository.getFavoriteCards();
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

    public void onLikeClick(Card card) {
        boolean isCardLiked = cardsRepository.isUrlLiked(card.url());

        compositeSubscription.add(
                cardsRepository.setLike(card, !isCardLiked)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
