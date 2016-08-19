package ru.yandex.detbr.school;

import java.util.ArrayList;
import java.util.List;


public class FakeSchoolsData implements SchoolsData {
    @Override
    public List<String> getSchoolsList() {
        List<String> result = new ArrayList<>();
        result.add("Школа 1");
        result.add("Школа 2");
        result.add("Школа 345678");
        result.add("Школа №4");
        return result;
    }
}
