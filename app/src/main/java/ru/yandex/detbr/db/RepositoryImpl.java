package ru.yandex.detbr.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.db.tables.CardsTable;

/**
 * Created by user on 30.08.16.
 */

public class RepositoryImpl implements Repository {
    @NonNull
    private final StorIOSQLite storIOSQLite;

    public RepositoryImpl(@NonNull StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public void saveCardToRepository(String title, String url, @Nullable String cover, boolean like) {
        // TODO rx
        Thread thread = new Thread(() -> {
            Card card = Card.builder()
                    .title(title)
                    .url(url)
                    .cover(cover == null? getCoverUrl(url) : cover)
                    .like(like)
                    .build();
            saveCard(card);
        });
        thread.start();
    }

    @Override
    public void saveCardToRepository(@NonNull Card card) {
        // TODO rx
        Thread thread = new Thread(() -> {
            saveCard(card);
        });
        thread.start();
    }

    private void saveCard(@NonNull Card card) {
        storIOSQLite
                .put()
                .object(card)
                .prepare()
                .executeAsBlocking();
    }

    private String getCoverUrl(@NonNull String url) {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements images = doc.select("img[src]");
            if (!images.isEmpty()) {
                return images.get(0).attr("src");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeLike(@NonNull String url) {
        // TODO rx
        Thread thread = new Thread(() -> {
            storIOSQLite
                    .executeSQL()
                    .withQuery(RawQuery.builder()
                            .query("UPDATE " + CardsTable.TABLE + " SET "
                                    + CardsTable.COLUMN_LIKE + " = ~" + CardsTable.COLUMN_LIKE + "&1"
                                    + " WHERE " + CardsTable.COLUMN_URL + " = ?")
                            .args(url)
                            .build())
                    .prepare()
                    .executeAsBlocking();
        });
        thread.start();
    }

    @Override
    public boolean getLikeFromUrl(@NonNull String url) {
        Card card = storIOSQLite
                .get()
                .object(Card.class)
                .withQuery(Query.builder()
                    .table(CardsTable.TABLE)
                    .where(CardsTable.COLUMN_URL + " = ?")
                    .whereArgs(url)
                    .build())
                .prepare()
                .executeAsBlocking();
        return card != null && card.getLike();
    }
}
