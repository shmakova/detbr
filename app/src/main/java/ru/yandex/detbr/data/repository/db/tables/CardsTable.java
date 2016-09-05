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

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    @NonNull
    public static String fillDatabaseWithDefaultCards() {
        return "INSERT INTO " + TABLE +
                " (" + COLUMN_TITLE + ", " + COLUMN_URL + ", " + COLUMN_IMAGE + ", " + COLUMN_LIKE + ") VALUES "
                + "(" + "\"LEGO Angry Birds\", " + "\"http://www.lego.com/ru-ru/angrybirdsmovie?icmp=CORUFRAngryBirds\", "
                + "\"http://cache.lego.com/r/www/-/media/catalogs/themes/franchises/theme%20cards%202016/angrybirds.jpg\", " + "1" + "), "
                + "(" + "\"Тайная жизнь домашних животных\", " + "\"https://www.kinopoisk.ru/film/743088\", "
                + "\"https://www.kinopoisk.ru/images/film_big/743088.jpg\", " + "1" + "), "
                + "(" + "\"Смотреть Гравити Фолз\", " + "\"https://yandex.ru/video/search?text=гравити%20фолз\", "
                + "\"http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg\", " + "1" + ");";
    }
}
