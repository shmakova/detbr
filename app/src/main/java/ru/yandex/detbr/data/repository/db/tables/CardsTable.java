package ru.yandex.detbr.data.repository.db.tables;


import android.support.annotation.NonNull;

public final class CardsTable {
    @NonNull
    public static final String TABLE = "cards_table";

    @NonNull
    public static final String COLUMN_ID = "rowid";

    @NonNull
    public static final String COLUMN_TITLE = "title";

    @NonNull
    public static final String COLUMN_URL = "url";

    @NonNull
    public static final String COLUMN_IMAGE = "image";

    @NonNull
    public static final String COLUMN_LIKE = "like";

    private CardsTable() {
    }

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_URL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_LIKE + " INTEGER NOT NULL"
                + ");";
    }
}
