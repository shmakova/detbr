package ru.yandex.detbr.data.tabs;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.List;

import ru.yandex.detbr.data.tabs.tables.TabsTable;
import rx.Observable;

/**
 * Created by shmakova on 29.08.16.
 */

public class TabsRepositoryImpl implements TabsRepository {
    private final StorIOSQLite storIOSQLite;

    public TabsRepositoryImpl(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public Observable<List<Tab>> getSavedTabs() {
        return storIOSQLite
                .get()
                .listOfObjects(Tab.class)
                .withQuery(Query.builder()
                        .table(TabsTable.TABLE)
                        .orderBy(TabsTable.COLUMN_ID + " DESC")
                        .build())
                .prepare()
                .asRxObservable()
                .first();
    }

    @Override
    public Observable<PutResult> addTab(Tab tab) {
        return storIOSQLite
                .put()
                .object(tab)
                .prepare()
                .asRxObservable()
                .first();
    }

    @Override
    public Observable<DeleteResult> removeTab(Tab tab) {
        return storIOSQLite
                .delete()
                .object(tab)
                .prepare()
                .asRxObservable()
                .first();
    }

    @Override
    public Observable<Object> removeLastTab() {
        return storIOSQLite
                .executeSQL()
                .withQuery(RawQuery.builder()
                        .query("DELETE FROM " + TabsTable.TABLE + " WHERE " + TabsTable.COLUMN_ID + " = "
                                + "(SELECT MAX(" + TabsTable.COLUMN_ID + ") FROM " + TabsTable.TABLE + ")")
                        .build())
                .prepare()
                .asRxObservable()
                .first();
    }
}
