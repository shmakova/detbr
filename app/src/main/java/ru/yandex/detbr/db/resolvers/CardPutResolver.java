package ru.yandex.detbr.db.resolvers;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import ru.yandex.detbr.cards.Card;


public class CardPutResolver extends PutResolver<Card> {
    @NonNull
    @Override
    public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull Card object) {
        return storIOSQLite
                .put()
                .object(object)
                .prepare()
                .executeAsBlocking();
    }
}
