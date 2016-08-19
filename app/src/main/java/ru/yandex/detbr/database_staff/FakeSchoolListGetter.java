package ru.yandex.detbr.database_staff;

import java.util.ArrayList;
import java.util.List;


public class FakeSchoolListGetter implements SchoolListGetter {
    @Override
    public List<String> getListOfSchools() {
        List<String> result = new ArrayList<>();
        result.add("Школа 1");
        result.add("Школа 2");
        result.add("Школа 345678");
        result.add("Школа №4");
        return result;
    }
}
