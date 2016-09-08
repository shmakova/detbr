package ru.yandex.detbr.data.tabs;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.List;

import ru.yandex.detbr.data.repository.db.tables.TabsTable;
import ru.yandex.detbr.data.tabs.models.Tab;
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
    public void addTab(Tab tab) {
        Thread thread = new Thread(() -> {
            storIOSQLite
                    .put()
                    .object(tab)
                    .prepare()
                    .executeAsBlocking();
        });
        thread.start();
    }

    @Override
    public void removeTab(Tab tab) {
        // TODO make not on main thread
        storIOSQLite
                .delete()
                .object(tab)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public void removeLastTab() {
        Thread thread = new Thread(() -> {
            storIOSQLite
                    .executeSQL()
                    .withQuery(RawQuery.builder()
                            .query("DELETE FROM " + TabsTable.TABLE + " WHERE " + TabsTable.COLUMN_ID + " = "
                                    + "(SELECT MAX(" + TabsTable.COLUMN_ID + ") FROM " + TabsTable.TABLE + ")")
                            .build())
                    .prepare()
                    .executeAsBlocking();
        });
        thread.start();
    }
}
