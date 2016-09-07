package ru.yandex.detbr.data.repository.db.resolvers;


import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import ru.yandex.detbr.data.repository.db.tables.CardsTable;
import ru.yandex.detbr.data.repository.models.Card;

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

        return Card.builder()
                .title(title)
                .url(url)
                .image(image)
                .site(site)
                .favicon(favicon)
                .color(color)
                .like(like)
                .build();
    }
}
