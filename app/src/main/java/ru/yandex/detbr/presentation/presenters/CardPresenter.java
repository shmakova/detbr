package ru.yandex.detbr.presentation.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.managers.LikeManager;
import ru.yandex.detbr.presentation.views.CardItemView;

/**
 * Created by shmakova on 04.09.16.
 */

public class CardPresenter extends MvpBasePresenter<CardItemView> {

    private final LikeManager likeManager;

    public CardPresenter(LikeManager likeManager) {
        this.likeManager = likeManager;
    }

    public void onLikeClick(Card card) {
        likeManager.setLike(card);
    }

    public void loadCard(Card card) {
        if (card != null && isViewAttached()) {
            getView().setTitle(card.title());
            getView().setSite(card.getSiteName());

            getView().setLike(likeManager.isUrlLiked(card.url()));

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
        }
    }
}
