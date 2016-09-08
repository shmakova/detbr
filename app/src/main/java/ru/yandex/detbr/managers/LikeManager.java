package ru.yandex.detbr.managers;

import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.cards.CardsRepository;

/**
 * Created by shmakova on 04.09.16.
 */

public class LikeManager {

    private final CardsRepository cardsRepository;

    public LikeManager(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public boolean isUrlLiked(String url) {
        return cardsRepository.isCardLiked(url);
    }

    public void setLike(Card card) {
        if (!cardsRepository.isCardExist(card.url())) {
            cardsRepository.saveFavouriteCard(card);
        }

        cardsRepository.toggleLike(card.url());
    }
}
