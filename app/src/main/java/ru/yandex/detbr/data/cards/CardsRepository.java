package ru.yandex.detbr.data.cards;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.yandex.detbr.data.categories.Category;
import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public interface CardsRepository {
    Observable<List<Card>> getCardsList();

    Observable<List<Card>> getFavouriteCards();

    Observable<List<Card>> getCardsByCategory(Category category);

    void saveFavouriteCard(String title, String url, @Nullable String cover, boolean like);

    void saveFavouriteCard(@NonNull Card card);

    void saveCard(Card card);

    void toggleLike(@NonNull String url);

    boolean getLikeFromUrl(@NonNull String url);

    boolean isCardExist(@NonNull String url);

    boolean isCardLiked(@NonNull String url);
}
