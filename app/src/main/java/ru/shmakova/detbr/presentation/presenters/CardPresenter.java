package ru.shmakova.detbr.presentation.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.presentation.views.CardItemView;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shmakova on 04.09.16.
 */

public class CardPresenter extends MvpBasePresenter<CardItemView> {

    private final CardsRepository cardsRepository;
    private final CompositeSubscription compositeSubscription;

    public CardPresenter(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
        compositeSubscription = new CompositeSubscription();
    }

    public void onLikeClick(Card card) {
        boolean isCardLiked = cardsRepository.isUrlLiked(card.url());

        compositeSubscription.add(
                cardsRepository.setLike(card, !isCardLiked)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                it -> {
                                    if (isViewAttached()) {
                                        getView().setLike(!isCardLiked);
                                    }
                                }));
    }

    public void loadCard(Card card) {
        if (card != null && isViewAttached()) {
            getView().setTitle(card.title());
            getView().setSite(card.getSiteName());

            getView().setLike(cardsRepository.isUrlLiked(card.url()));

            if (card.image() != null && !card.image().isEmpty()) {
                getView().setImage(card.image());
            }

            if (card.color() != null && !card.color().isEmpty()) {
                getView().setBackgroundColor(card.color());
            }

            if (card.favicon() != null && !card.favicon().isEmpty()) {
                getView().setFavicon(card.favicon());
            }

            if (card.description() != null && !card.description().isEmpty()) {
                getView().setDescription(card.description());
            }

            if (card.dark()) {
                getView().setWhiteText();
            }
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
