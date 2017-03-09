package ru.shmakova.detbr.data.categories;

import java.util.List;

import rx.Observable;

public interface CategoriesRepository {
    Observable<List<Category>> getCategories();
}
