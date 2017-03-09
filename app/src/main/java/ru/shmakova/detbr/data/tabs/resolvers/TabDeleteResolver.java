package ru.shmakova.detbr.data.tabs.resolvers;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.data.tabs.tables.TabsTable;

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
