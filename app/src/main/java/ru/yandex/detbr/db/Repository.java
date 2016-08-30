package ru.yandex.detbr.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.yandex.detbr.cards.Card;

/**
 * Created by user on 30.08.16.
 */

public interface Repository {
    public void saveCardToRepository(String title, String url, @Nullable String cover, boolean like);

    public void saveCardToRepository(@NonNull Card card);

    public void changeLike(@NonNull String url);

    public boolean getLikeFromUrl(@NonNull String url);
}
