package ru.yandex.detbr.data.repository.db.tables;

import android.support.annotation.NonNull;

/**
 * Created by user on 02.09.16.
 */

public final class TabsTable {
    @NonNull
    public static final String TABLE = "tabs_table";

    @NonNull
    public static final String COLUMN_ID = "rowid";

    @NonNull
    public static final String COLUMN_TITLE = "title";

    @NonNull
    public static final String COLUMN_URL = "url";

    @NonNull
    public static final String COLUMN_PREVIEW = "preview";

    private TabsTable() {
    }

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_URL + " TEXT UNIQUE, "
                + COLUMN_PREVIEW + " BLOB"
                + ");";
    }
}