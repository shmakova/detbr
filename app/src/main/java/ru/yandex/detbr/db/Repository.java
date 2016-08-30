package ru.yandex.detbr.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.yandex.detbr.cards.Card;

/**
 * Created by user on 30.08.16.
 */

public interface Repository {
    void saveCardToRepository(String title, String url, @Nullable String cover, boolean like);

    void saveCardToRepository(@NonNull Card card);

    void changeLike(@NonNull String url);

    boolean getLikeFromUrl(@NonNull String url);
}
