package ru.yandex.detbr.data.cards;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.detbr.data.cards.tables.CardsTable;
import ru.yandex.detbr.data.categories.Category;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shmakova on 08.09.16.
 */

public class CardsRepositoryImpl implements CardsRepository {

    private final StorIOSQLite storIOSQLite;
    private final DatabaseReference databaseReference;

    public CardsRepositoryImpl(StorIOSQLite storIOSQLite, DatabaseReference databaseReference) {
        this.storIOSQLite = storIOSQLite;
        this.databaseReference = databaseReference;
    }

    @Override
    public Observable<List<Card>> getCardsList() {
        return RxFirebaseDatabase
                .observeValueEvent(databaseReference.child("cards").limitToLast(100))
                .map(DataSnapshot::getChildren)
                .flatMap(dataSnapshots -> Observable.from(dataSnapshots)
                        .map(Card::create)
                        .toList()
                        .map(cards -> {
                            Collections.reverse(cards);
                            return cards;
                        })
                )
                .first();
    }

    @Override
    public Observable<List<Card>> getFavouriteCards() {
        return storIOSQLite
                .get()
                .listOfObjects(Card.class)
                .withQuery(Query.builder()
                        .table(CardsTable.TABLE)
                        .where(CardsTable.COLUMN_LIKE + " = ?")
                        .whereArgs("1")
                        .orderBy(CardsTable.COLUMN_ID + " DESC")
                        .build())
                .prepare()
                .asRxObservable()
                .first();
    }

    @Override
    public Observable<List<Card>> getCardsByCategory(Category category) {
        return RxFirebaseDatabase
                .observeValueEvent(databaseReference
                        .child("cards")
                        .orderByChild("category")
                        .equalTo(category.alias())
                        .limitToLast(100)
                )
                .map(DataSnapshot::getChildren)
                .flatMap(dataSnapshots -> Observable.from(dataSnapshots)
                        .map(Card::create)
                        .toList()
                        .map(cards -> {
                            Collections.reverse(cards);
                            return cards;
                        })
                )
                .first();
    }

    @Override
    public void saveFavouriteCard(String title, String url, @Nullable String image, boolean like) {
        // TODO rx
        Thread thread = new Thread(() -> {
            Card card = Card.builder()
                    .title(title)
                    .url(url)
                    .image(image == null ? getImageUrl(url) : image)
                    .like(like)
                    .build();
            saveCardToDb(card);
        });
        thread.start();
    }

    @Override
    public void saveFavouriteCard(@NonNull Card card) {
        // TODO rx
        Thread thread = new Thread(() -> {
            saveCardToDb(card);
        });
        thread.start();
    }

    private void saveCardToDb(@NonNull Card card) {
        storIOSQLite
                .put()
                .object(card)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public boolean isCardExist(@NonNull String url) {
        Card card = storIOSQLite
                .get()
                .object(Card.class)
                .withQuery(Query.builder()
                        .table(CardsTable.TABLE)
                        .where(CardsTable.COLUMN_URL + " LIKE ? ")
                        .whereArgs(url)
                        .build())
                .prepare()
                .executeAsBlocking();
        return card != null;
    }

    @Override
    public boolean isCardLiked(@NonNull String url) {
        Card card = storIOSQLite
                .get()
                .object(Card.class)
                .withQuery(Query.builder()
                        .table(CardsTable.TABLE)
                        .where(CardsTable.COLUMN_URL + " LIKE ? AND " + CardsTable.COLUMN_LIKE + " = ?")
                        .whereArgs(url, "1")
                        .build())
                .prepare()
                .executeAsBlocking();
        return card != null;
    }

    private String getImageUrl(@NonNull String url) {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements images = doc.select("img[src~=(https?:\\/\\/.*\\.(?:png|jpe?g))]");

            if (!images.isEmpty()) {
                return images.get(0).attr("src");
            }
        } catch (IOException e) {
            Timber.e(e, "Error retrieving image from page");
        }
        return null;
    }

    @Override
    public void toggleLike(@NonNull String url) {
        // TODO rx
        Thread thread = new Thread(() -> {
            storIOSQLite
                    .executeSQL()
                    .withQuery(RawQuery.builder()
                            .query("UPDATE " + CardsTable.TABLE + " SET "
                                    + CardsTable.COLUMN_LIKE + " = ~" + CardsTable.COLUMN_LIKE + "&1"
                                    + " WHERE " + CardsTable.COLUMN_URL + " LIKE ?")
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
                        .where(CardsTable.COLUMN_URL + " LIKE ?")
                        .whereArgs(url)
                        .build())
                .prepare()
                .executeAsBlocking();
        return card != null && card.like();
    }

    @Override
    public void saveCard(Card card) {
        databaseReference.child("cards")
                .orderByChild("url")
                .equalTo(card.url())
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            pushCardToFirebase(card);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e(databaseError.getMessage(), "Save card error");
                    }
                });
    }

    private void pushCardToFirebase(Card card) {
        String key = databaseReference.child("cards").push().getKey();
        databaseReference.child("cards")
                .orderByChild("url")
                .equalTo(card.url());

        Observable.just(card)
                .subscribeOn(Schedulers.io())
                .map(it -> {
                    Map<String, Object> cardValues = new HashMap<>();
                    cardValues.put("url", it.url());
                    cardValues.put("title", it.title());
                    String image = getImageUrl(it.url());

                    if (image == null || image.isEmpty()) {
                        cardValues.put("type", Card.TEXT_TYPE);
                    } else {
                        cardValues.put("image", image);
                        cardValues.put("type", Card.PLAIN_IMAGE_TYPE);
                    }

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/cards/" + key, cardValues);

                    databaseReference.updateChildren(childUpdates);

                    return it;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> Timber.d(card.toString()),
                        throwable -> Timber.e(throwable.getMessage(), "Push card to firebase"),
                        () -> Timber.d("Completed: Card was sent"));
    }
}
