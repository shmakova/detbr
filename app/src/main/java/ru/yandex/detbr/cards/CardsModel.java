package ru.yandex.detbr.cards;

import java.util.List;

import ru.yandex.detbr.categories.Category;

/**
 * Created by shmakova on 21.08.16.
 */

public interface CardsModel {
    List<Card> getCardsListBySchool();

    List<Card> getFavouriteCards();

    List<Card> getCardsByCategory(Category category);
}
