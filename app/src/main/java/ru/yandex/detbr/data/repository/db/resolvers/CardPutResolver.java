package ru.yandex.detbr.data.repository.db.resolvers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import ru.yandex.detbr.data.repository.db.tables.CardsTable;
import ru.yandex.detbr.data.repository.models.Card;


public class CardPutResolver extends DefaultPutResolver<Card> {
    @Override
    @NonNull
    protected InsertQuery mapToInsertQuery(@NonNull Card card) {
        return InsertQuery.builder()
                .table(CardsTable.TABLE)
                .build();
    }

    @Override
    @NonNull
    protected UpdateQuery mapToUpdateQuery(@NonNull Card card) {
        return UpdateQuery.builder()
                .table(CardsTable.TABLE)
                .where(CardsTable.COLUMN_URL + " LIKE ?")
                .whereArgs(card.getUrl())
                .build();
    }

    @Override
    @NonNull
    public ContentValues mapToContentValues(@NonNull Card card) {
        ContentValues contentValues = new ContentValues(4);

        contentValues.put(CardsTable.COLUMN_COVER, card.getCover());
        contentValues.put(CardsTable.COLUMN_LIKE, card.getLike());
        contentValues.put(CardsTable.COLUMN_TITLE, card.getTitle());
        contentValues.put(CardsTable.COLUMN_URL, card.getUrl());

        return contentValues;
    }
}
