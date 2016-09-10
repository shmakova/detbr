package ru.yandex.detbr.data.cards;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
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
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shmakova on 08.09.16.
 */

public class CardsRepositoryImpl implements CardsRepository {
    private final static String CARDS = "cards";

    private final StorIOSQLite storIOSQLite;
    private final DatabaseReference databaseReference;

    public CardsRepositoryImpl(StorIOSQLite storIOSQLite, DatabaseReference databaseReference) {
        this.storIOSQLite = storIOSQLite;
        this.databaseReference = databaseReference;
    }

    @Override
    public Observable<List<Card>> getCardsList() {
        return RxFirebaseDatabase
                .observeValueEvent(databaseReference.child(CARDS).limitToLast(100))
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
                        .child(CARDS)
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
    public Observable<PutResult> saveFavouriteCard(@NonNull Card card) {
        return saveCardToDb(card);
    }

    private Observable<PutResult> saveCardToDb(@NonNull Card card) {
        return storIOSQLite
                .put()
                .object(card)
                .prepare()
                .asRxObservable();
    }

    @Override
    public Observable<Card> getCardByUrl(@NonNull String url) {
        return storIOSQLite
                .get()
                .object(Card.class)
                .withQuery(Query.builder()
                        .table(CardsTable.TABLE)
                        .where(CardsTable.COLUMN_URL + " LIKE ? ")
                        .whereArgs(url)
                        .build())
                .prepare()
                .asRxObservable()
                .first();
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
    public Observable<Object> toggleLike(@NonNull Card card) {
        return storIOSQLite
                .executeSQL()
                .withQuery(RawQuery.builder()
                        .query("UPDATE " + CardsTable.TABLE + " SET "
                                + CardsTable.COLUMN_LIKE + " = ~" + CardsTable.COLUMN_LIKE + "&1"
                                + " WHERE " + CardsTable.COLUMN_URL + " LIKE ?")
                        .args(card.url())
                        .build())
                .prepare()
                .asRxObservable();
    }

    public Observable<Card> saveCard(Card card) {
        return RxFirebaseDatabase
                .observeValueEvent(
                        databaseReference
                                .child(CARDS)
                                .orderByChild("url")
                                .equalTo(card.url())
                                .limitToLast(1))
                .map(DataSnapshot::getChildren)
                .flatMap(dataSnapshots -> Observable.from(dataSnapshots)
                        .map(Card::create)
                        .toList()
                        .map(cards -> cards.isEmpty())
                )
                .map(isCardExist -> {
                    if (isCardExist) {
                        return null;
                    } else {
                        return card;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(PUSH_CARD_TO_FIREBASE)
                .map(SAVE_CARD_TO_DATABASE)
                .first();

    }


    private final Func1<Card, Card> PUSH_CARD_TO_FIREBASE =
            new Func1<Card, Card>() {
                @Override
                public Card call(Card card) {
                    String key = databaseReference.child(CARDS).push().getKey();

                    databaseReference.child(CARDS)
                            .orderByChild("url")
                            .equalTo(card.url());

                    Map<String, Object> cardValues = new HashMap<>();
                    cardValues.put("url", card.url());
                    cardValues.put("title", card.title());
                    String image = getImageUrl(card.url());

                    if (image == null || image.isEmpty()) {
                        cardValues.put("type", Card.TEXT_TYPE);
                    } else {
                        cardValues.put("image", image);
                        cardValues.put("type", Card.PLAIN_IMAGE_TYPE);
                    }

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/cards/" + key, cardValues);

                    databaseReference.updateChildren(childUpdates);

                    return card;
                }
            };

    private final Func1<Card, Card> SAVE_CARD_TO_DATABASE =
            new Func1<Card, Card>() {
                @Override
                public Card call(Card card) {
                    String key = databaseReference.child(CARDS).push().getKey();

                    databaseReference.child(CARDS)
                            .orderByChild("url")
                            .equalTo(card.url());

                    Map<String, Object> cardValues = new HashMap<>();
                    cardValues.put("url", card.url());
                    cardValues.put("title", card.title());
                    String image = getImageUrl(card.url());

                    if (image == null || image.isEmpty()) {
                        cardValues.put("type", Card.TEXT_TYPE);
                    } else {
                        cardValues.put("image", image);
                        cardValues.put("type", Card.PLAIN_IMAGE_TYPE);
                    }

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/cards/" + key, cardValues);

                    databaseReference.updateChildren(childUpdates);

                    return card;
                }
            };
}
