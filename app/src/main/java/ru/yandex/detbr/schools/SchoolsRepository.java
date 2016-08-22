package ru.yandex.detbr.schools;


import java.util.List;

public interface SchoolsRepository {
    String SCHOOL_TAG = "SCHOOL_TAG";

    List<String> getSchoolsList();
}
