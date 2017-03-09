package ru.shmakova.detbr.data.cards.resolvers;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.tables.CardsTable;

public class CardDeleteResolver extends DefaultDeleteResolver<Card> {
    @Override
    @NonNull
    protected DeleteQuery mapToDeleteQuery(@NonNull Card card) {
        return DeleteQuery.builder()
                .table(CardsTable.TABLE)
                .where(CardsTable.COLUMN_URL + " LIKE ?")
                .whereArgs(card.url())
                .build();
    }
}
