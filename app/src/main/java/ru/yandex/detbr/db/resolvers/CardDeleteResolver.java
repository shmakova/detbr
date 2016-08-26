package ru.yandex.detbr.db.resolvers;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import ru.yandex.detbr.cards.Card;

/**
 * Created by user on 26.08.16.
 */

public class CardDeleteResolver extends DeleteResolver<Card> {
    @NonNull
    @Override
    public DeleteResult performDelete(@NonNull StorIOSQLite storIOSQLite, @NonNull Card object) {
        return null;
    }
}
