package ru.yandex.detbr.db.tables;


import android.support.annotation.NonNull;

public class CardsTable {
    @NonNull
    public static final String TABLE = "cards_table";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_TITLE = "title";

    @NonNull
    public static final String COLUMN_URL = "url";

    @NonNull
    public static final String COLUMN_COVER = "cover";

    @NonNull
    public static final String COLUMN_LIKE = "like";

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_URL + " TEXT NOT NULL, "
                + COLUMN_COVER + " TEXT, "
                + COLUMN_LIKE + " INTEGER NOT NULL"
                + ");";
    }
}
