package ru.yandex.detbr.data.repository.db.resolvers;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import ru.yandex.detbr.data.repository.db.tables.CardsTable;
import ru.yandex.detbr.data.repository.models.Card;

/**
 * Created by user on 28.08.16.
 */

public class CardDeleteResolver extends DefaultDeleteResolver<Card> {
    @Override
    @NonNull
    protected DeleteQuery mapToDeleteQuery(@NonNull Card card) {
        return DeleteQuery.builder()
                .table(CardsTable.TABLE)
                .where(CardsTable.COLUMN_URL + " = ?")
                .whereArgs(card.getUrl())
                .build();
    }
}

