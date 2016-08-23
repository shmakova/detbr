package ru.yandex.detbr.ui.views;

import java.util.List;

import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.cards.Category;

/**
 * Created by shmakova on 21.08.16.
 */

public interface CardsView {
    void setCardsData(List<Card> cards);

    void setCategories(List<Category> categories);
}
