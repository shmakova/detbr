package ru.yandex.detbr.data.schools;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public class SchoolsRepositoryImpl implements SchoolsRepository {
    String SCHOOL_TAG = "SCHOOL_TAG";

    private final SharedPreferences sharedPreferences;

    public SchoolsRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Observable<List<String>> getSchoolsList() {
        // TODO: 05.09.16 to firebase
        List<String> schools = new ArrayList<>();
        schools.add("ГБОУ г. Москвы лицей №1535");
        schools.add("ГБОУ г. Москвы центр образования №57 «Пятьдесят седьмая школа»");
        schools.add("ГБОУ г. Москвы «Многопрофильный лицей №1501»");
        schools.add("ГБОУ г. Москвы лицей «Вторая школа»");
        schools.add("ГБОУ г. Москвы «Школа-интернат «Интеллектуал»");
        return Observable.just(schools);
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
