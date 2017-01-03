package ru.shmakova.detbr.data.cards.resolvers;


import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.tables.CardsTable;

public class CardGetResolver extends DefaultGetResolver<Card> {
    @Override
    @NonNull
    public Card mapFromCursor(@NonNull Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_TITLE));
        String url = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_URL));
        String image = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_IMAGE));
        String site = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_SITE));
        String favicon = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_FAVICON));
        String color = cursor.getString(cursor.getColumnIndex(CardsTable.COLUMN_COLOR));
        boolean like = cursor.getInt(cursor.getColumnIndex(CardsTable.COLUMN_LIKE)) == 1;
        boolean dark = cursor.getInt(cursor.getColumnIndex(CardsTable.COLUMN_DARK)) == 1;

        return Card.builder()
                .title(title)
                .url(url)
                .image(image)
                .site(site)
                .favicon(favicon)
                .color(color)
                .like(like)
                .dark(dark)
                .build();
    }
}
