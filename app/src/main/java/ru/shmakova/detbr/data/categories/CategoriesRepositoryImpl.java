package ru.shmakova.detbr.data.categories;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.List;

import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public class CategoriesRepositoryImpl implements CategoriesRepository {
    private final DatabaseReference databaseReference;

    public CategoriesRepositoryImpl(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    @Override
    public Observable<List<Category>> getCategories() {
        return RxFirebaseDatabase
                .observeValueEvent(databaseReference.child("categories"))
                .map(DataSnapshot::getChildren)
                .flatMap(dataSnapshots -> Observable.from(dataSnapshots)
                        .map(Category::create)
                        .toList())
                .first();
    }
}