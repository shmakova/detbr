package ru.yandex.detbr.data.categories;

import java.util.List;

import rx.Observable;

/**
 * Created by shmakova on 08.09.16.
 */

public interface CategoriesRepository {
    Observable<List<Category>> getCategories();
}
