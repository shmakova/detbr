package ru.yandex.detbr.ui.managers;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;

/**
 * Created by shmakova on 04.09.16.
 */

public class LikeManager {

    private final DataRepository dataRepository;

    public LikeManager(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean isUrlLiked(String url) {
        return dataRepository.isCardLiked(url);
    }

    public void setLike(Card card) {
        if (!dataRepository.isCardExist(card.url())) {
            dataRepository.saveFavouriteCard(card);
        }

        dataRepository.changeLike(card.url());
    }
}
