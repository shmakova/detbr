package ru.yandex.detbr.schools;

import java.util.ArrayList;
import java.util.List;


public class SchoolsFakeRepository implements SchoolsRepository {
    @Override
    public List<String> getSchoolsList() {
        List<String> result = new ArrayList<>();
        result.add("ГБОУ г. Москвы лицей №1535");
        result.add("ГБОУ г. Москвы центр образования №57 «Пятьдесят седьмая школа»");
        result.add("ГБОУ г. Москвы «Многопрофильный лицей №1501»");
        result.add("ГБОУ г. Москвы лицей «Вторая школа»");
        result.add("ГБОУ г. Москвы «Школа-интернат «Интеллектуал»");
        return result;
    }
}
