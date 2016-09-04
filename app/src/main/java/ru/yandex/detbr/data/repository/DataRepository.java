package ru.yandex.detbr.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import rx.Observable;

/**
 * Created by shmakova on 28.08.16.
 */

public interface DataRepository {
    String SCHOOL_TAG = "SCHOOL_TAG";

    Observable<List<Category>> getCategories();

    Observable<List<Card>> getCardsList();

    Observable<List<Card>> getFavouriteCards();

    Observable<List<Card>> getCardsByCategory(Category category);

    Observable<List<String>> getSchoolsList();

    void saveCardToRepository(String title, String url, @Nullable String cover, boolean like);

    void saveCardToRepository(@NonNull Card card);

    void changeLike(@NonNull String url);

    boolean getLikeFromUrl(@NonNull String url);

    boolean isCardExist(@NonNull String url);

    boolean isCardLiked(@NonNull String url);
}
