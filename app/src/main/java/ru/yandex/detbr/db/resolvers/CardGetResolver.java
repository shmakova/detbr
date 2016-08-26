package ru.yandex.detbr.db.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import ru.yandex.detbr.cards.Card;

/**
 * Created by user on 26.08.16.
 */

public class CardGetResolver extends GetResolver<Card> {
    @NonNull
    @Override
    public Card mapFromCursor(@NonNull Cursor cursor) {
        return null;
    }

    @NonNull
    @Override
    public Cursor performGet(@NonNull StorIOSQLite storIOSQLite, @NonNull RawQuery rawQuery) {
        return null;
    }

    @NonNull
    @Override
    public Cursor performGet(@NonNull StorIOSQLite storIOSQLite, @NonNull Query query) {
        return null;
    }
}
