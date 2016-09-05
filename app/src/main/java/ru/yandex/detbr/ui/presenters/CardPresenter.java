package ru.yandex.detbr.ui.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.ui.managers.LikeManager;
import ru.yandex.detbr.ui.views.CardView;
import ru.yandex.detbr.utils.UrlUtils;

/**
 * Created by shmakova on 04.09.16.
 */

public class CardPresenter extends MvpBasePresenter<CardView> {

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

            if (card.site() == null || card.site().isEmpty()) {
                getView().setSite(UrlUtils.getHost(card.url()));
            } else {
                getView().setSite(card.site());
            }

            getView().setLike(likeManager.isUrlLiked(card.url()));

            if (card.image() != null && !card.image().isEmpty()) {
                getView().setImage(card.image());
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
