package ru.yandex.detbr.data.cards.tables;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    @Nullable
    public static final String COLUMN_SITE = "site";

    @Nullable
    public static final String COLUMN_FAVICON = "favicon";

    @Nullable
    public static final String COLUMN_COLOR = "color";

    @NonNull
    public static final String COLUMN_DARK = "dark";

    private CardsTable() {
    }

    @NonNull
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_URL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_SITE + " TEXT, "
                + COLUMN_FAVICON + " TEXT, "
                + COLUMN_COLOR + " TEXT, "
                + COLUMN_DARK + " INTEGER NOT NULL, "
                + COLUMN_LIKE + " INTEGER NOT NULL"
                + ");";
    }
}
