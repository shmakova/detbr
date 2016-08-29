package ru.yandex.detbr.cards.resolvers;


import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.cards.CardUtils;
import ru.yandex.detbr.db.tables.CardsTable;

public class CardGetResolver extends DefaultGetResolver<Card> {
    @Override
    @NonNull
    public Card mapFromCursor(@NonNull Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_TITLE));
        String url = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_URL));
        String cover = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_COVER));
        boolean like = cursor.getInt(cursor.getColumnIndex(CardsTable.COLUMN_LIKE)) == 1;

        return CardUtils.getCard(title, url, cover, like);
    }
}
