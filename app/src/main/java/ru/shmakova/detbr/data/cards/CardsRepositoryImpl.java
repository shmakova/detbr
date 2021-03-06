package ru.shmakova.detbr.data.cards;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.shmakova.detbr.data.cards.resolvers.CardPutResolver;
import ru.shmakova.detbr.data.cards.tables.CardsTable;
import ru.shmakova.detbr.data.categories.Category;
import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

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
    public Observable<List<Card>> getFavoriteCards() {
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

    @Override
    public boolean isUrlLiked(@NonNull String url) {
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
    public Observable<PutResult> setLike(@NonNull Card card, boolean like) {
        return storIOSQLite
                .put()
                .object(card.withLike(like))
                .withPutResolver(new CardPutResolver())
                .prepare()
                .asRxObservable();
    }

    public Observable<Card> saveCard(Card card) {
        return Observable.just(card)
                .map(loadImageForCard)
                .map(saveCardToDatabase)
                .flatMap(pushCardToFirebase)
                .first();
    }


    private final Func1<Card, Observable<Card>> pushCardToFirebase =
            new Func1<Card, Observable<Card>>() {
                @Override
                public Observable<Card> call(Card card) {
                    return RxFirebaseDatabase
                            .observeSingleValueEvent(
                                    databaseReference.child(CARDS)
                                            .orderByChild("url")
                                            .equalTo(card.url()))
                            .map(dataSnapshot -> {
                                if (dataSnapshot.getValue() == null) {
                                    String key = databaseReference.child(CARDS).push().getKey();

                                    Map<String, Object> cardValues = new HashMap<>();
                                    cardValues.put("url", card.url());
                                    cardValues.put("title", card.title());

                                    if (card.image() == null || card.image().isEmpty()) {
                                        cardValues.put("type", Card.TEXT_TYPE);
                                    } else {
                                        cardValues.put("image", card.image());
                                        cardValues.put("type", Card.PLAIN_IMAGE_TYPE);
                                    }

                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("/cards/" + key, cardValues);

                                    databaseReference.updateChildren(childUpdates);
                                }

                                return card;
                            })
                            .first();
                }
            };

    private final Func1<Card, Card> saveCardToDatabase =
            new Func1<Card, Card>() {
                @Override
                public Card call(Card card) {
                    storIOSQLite
                            .put()
                            .object(card)
                            .prepare()
                            .executeAsBlocking();
                    return card;
                }
            };

    private final Func1<Card, Card> loadImageForCard =
            new Func1<Card, Card>() {
                @Override
                public Card call(Card card) {
                    return card.withImage(getImageUrl(card.url()));
                }
            };
}
