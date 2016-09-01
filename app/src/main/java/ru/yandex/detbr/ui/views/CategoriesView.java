package ru.yandex.detbr.ui.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.yandex.detbr.data.repository.models.Category;

/**
 * Created by shmakova on 24.08.16.
 */

public interface CategoriesView extends MvpLceView<List<Category>> {
    void showCategoryCards(Category category);
}
