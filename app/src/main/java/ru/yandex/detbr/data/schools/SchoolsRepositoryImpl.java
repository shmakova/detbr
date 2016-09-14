package ru.yandex.detbr.data.schools;

import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.List;

import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public class SchoolsRepositoryImpl implements SchoolsRepository {
    private final static String SCHOOL_TAG = "SCHOOL_TAG";
    private final static String SCHOOLS = "schools";

    private final SharedPreferences sharedPreferences;
    private final DatabaseReference databaseReference;

    public SchoolsRepositoryImpl(SharedPreferences sharedPreferences,
                                 DatabaseReference databaseReference) {
        this.sharedPreferences = sharedPreferences;
        this.databaseReference = databaseReference;
    }

    @Override
    public Observable<List<String>> getSchoolsList() {
        return RxFirebaseDatabase
                .observeValueEvent(databaseReference.child(SCHOOLS))
                .map(DataSnapshot::getChildren)
                .flatMap(dataSnapshots -> Observable.from(dataSnapshots)
                        .map(dataSnapshot -> dataSnapshot.getValue(String.class))
                        .toList())
                .first();
    }

    @Override
    public void saveSchool(String school) {
        sharedPreferences
                .edit()
                .putString(SCHOOL_TAG, school)
                .apply();
    }

    @Override
    public String loadSchool() {
        return sharedPreferences.getString(SCHOOL_TAG, null);
    }
}
