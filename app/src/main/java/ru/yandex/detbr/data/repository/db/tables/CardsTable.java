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
    public static final String COLUMN_COVER = "cover";

    @NonNull
    public static final String COLUMN_LIKE = "like";

    private CardsTable() {
    }

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_URL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_COVER + " TEXT, "
                + COLUMN_LIKE + " INTEGER NOT NULL"
                + ");";
    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    @NonNull
    public static String fillDatabaseWithDefaultCards() {
        return "INSERT INTO " + TABLE +
                " (" + COLUMN_TITLE + ", " + COLUMN_URL + ", " + COLUMN_COVER + ", " + COLUMN_LIKE + ") VALUES "
                + "(" + "\"LEGO Angry Birds\", " + "\"http://www.lego.com/ru-ru/angrybirdsmovie?icmp=CORUFRAngryBirds\", "
                + "\"http://cache.lego.com/r/www/-/media/catalogs/themes/franchises/theme%20cards%202016/angrybirds.jpg\", " + "0" + "), "
                + "(" + "\"Тайная жизнь домашних животных\", " + "\"https://www.kinopoisk.ru/film/743088\", "
                + "\"https://www.kinopoisk.ru/images/film_big/743088.jpg\", " + "0" + "), "
                + "(" + "\"Лонгборд Penny Original 22\", " + "\"https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls\", "
                + "\"https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg\", " + "0" + "), "
                + "(" + "\"Ледниковый период: Столкновение неизбежно\", " + "\"https://www.kinopoisk.ru/film/818145\", "
                + "\"https://www.kinopoisk.ru/images/film_big/818145.jpg\", " + "0" + "), "
                + "(" + "\"Выбираем велосипед для подростка\", " + "\"https://market.yandex.ru/articles/" +
                "vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet\", "
                + "\"https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg\", " + "0" + "), "
                + "(" + "\"5 МИРОВЫХ РЕКОРДОВ POKEMON GO\", " + "\"http://gopokemongo.ru/5-mirovyih-rekordov-pokemon-go.html\", "
                + "\"http://img.youtube.com/vi/tV9EErN3x-k/0.jpg\", " + "0" + "), "
                + "(" + "\"Варкрафт\", " + "\"https://www.kinopoisk.ru/film/277328\", "
                + "\"https://www.kinopoisk.ru/images/film_big/277328.jpg\", " + "0" + "), "
                + "(" + "\"Тест: Правильно ли вы произносите названия известных брендов?\", "
                + "\"https://www.adme.ru/tvorchestvo-dizajn/test-pravilno-li-vy-proiznosite-nazvaniya-izvestnyh-brendov-1081510/\", "
                + "\"https://files4.adme.ru/files/news/part_115/1152260/preview-16564065-650x341-98-1471597471.jpg\", " + "0" + "), "
                + "(" + "\"Люди Икс: Апокалипсис\", " + "\"https://www.kinopoisk.ru/film/814016\", "
                + "\"https://www.kinopoisk.ru/images/film_big/814016.jpg\", " + "0" + "), "
                + "(" + "\"11 комиксов о том, как изменилась наша жизнь с появлением интернета\", "
                + "\"https://www.adme.ru/zhizn-nostalgiya/11-komiksov-o-tom-kak-izmenilas-nasha-zhizn-s-poyavleniem-interneta-1299765/\", "
                + "\"https://files3.adme.ru/files/news/part_129/1299765/10455565-810-1000-53c212670e-1470661968.jpg\", " + "0" + "), "
                + "(" + "\"Смотреть Гравити Фолз\", " + "\"https://yandex.ru/video/search?text=гравити%20фолз\", "
                + "\"http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg\", " + "0" + ");";
    }
}
