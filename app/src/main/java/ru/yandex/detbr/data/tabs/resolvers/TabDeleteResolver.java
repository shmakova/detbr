package ru.yandex.detbr.data.tabs.resolvers;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import ru.yandex.detbr.data.repository.db.tables.TabsTable;
import ru.yandex.detbr.data.tabs.models.Tab;

/**
 * Created by user on 04.09.16.
 */

public class TabDeleteResolver extends DefaultDeleteResolver<Tab> {
    @Override
    @NonNull
    protected DeleteQuery mapToDeleteQuery(@NonNull Tab tab) {
        return DeleteQuery.builder()
                .table(TabsTable.TABLE)
                .where(TabsTable.COLUMN_URL + " LIKE ?")
                .whereArgs(tab.getUrl())
                .build();
    }
}
