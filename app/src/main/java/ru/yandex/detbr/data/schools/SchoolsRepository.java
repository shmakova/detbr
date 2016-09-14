package ru.yandex.detbr.data.schools;

import java.util.List;

import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public interface SchoolsRepository {
    Observable<List<String>> getSchoolsList();

    void saveSchool(String school);

    String loadSchool();
}
