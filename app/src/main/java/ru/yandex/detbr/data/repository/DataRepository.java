package ru.yandex.detbr.data.repository;

import java.util.List;

import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import rx.Observable;

/**
 * Created by shmakova on 28.08.16.
 */

public interface DataRepository {
    String SCHOOL_TAG = "SCHOOL_TAG";

    Observable<List<Category>> getCategories();

    Observable<List<Card>> getCardsListBySchool();

    Observable<List<Card>> getFavouriteCards();

    Observable<List<Card>> getCardsByCategory(Category category);

    Observable<List<String>> getSchoolsList();
}
