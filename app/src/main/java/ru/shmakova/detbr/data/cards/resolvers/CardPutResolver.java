package ru.shmakova.detbr.data.cards.resolvers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.tables.CardsTable;


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
                .whereArgs(card.url())
                .build();
    }

    @Override
    @NonNull
    public ContentValues mapToContentValues(@NonNull Card card) {
        ContentValues contentValues = new ContentValues(8);

        contentValues.put(CardsTable.COLUMN_IMAGE, card.image());
        contentValues.put(CardsTable.COLUMN_LIKE, card.like());
        contentValues.put(CardsTable.COLUMN_DARK, card.dark());
        contentValues.put(CardsTable.COLUMN_TITLE, card.title());
        contentValues.put(CardsTable.COLUMN_FAVICON, card.favicon());
        contentValues.put(CardsTable.COLUMN_SITE, card.site());
        contentValues.put(CardsTable.COLUMN_COLOR, card.color());
        contentValues.put(CardsTable.COLUMN_URL, card.url());

        return contentValues;
    }
}
