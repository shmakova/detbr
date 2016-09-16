package ru.yandex.detbr.data.cards;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import ru.yandex.detbr.data.categories.Category;
import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public interface CardsRepository {
    Observable<List<Card>> getCardsList();

    Observable<List<Card>> getFavoriteCards();

    Observable<List<Card>> getCardsByCategory(Category category);

    Observable<Card> saveCard(Card card);

    Observable<PutResult> setLike(@NonNull Card card, boolean like);

    Observable<Card> getCardByUrl(@NonNull String url);

    boolean isUrlLiked(@NonNull String url);
}
