package ru.shmakova.detbr.presentation.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.shmakova.detbr.data.categories.Category;
import ru.shmakova.detbr.data.categories.CategoryClick;

/**
 * Created by shmakova on 24.08.16.
 */

public interface CategoriesView extends MvpLceView<List<Category>> {
    void showCategoryCards(CategoryClick categoryClick);
}
