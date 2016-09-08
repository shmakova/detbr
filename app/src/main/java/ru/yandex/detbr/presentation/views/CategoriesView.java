package ru.yandex.detbr.presentation.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.yandex.detbr.data.categories.Category;

/**
 * Created by shmakova on 24.08.16.
 */

public interface CategoriesView extends MvpLceView<List<Category>> {
    void showCategoryCards(Category category);
}
