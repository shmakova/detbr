package ru.shmakova.detbr.categories;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.shmakova.detbr.data.categories.Category;
import ru.shmakova.detbr.data.categories.CategoryClick;

public interface CategoriesView extends MvpLceView<List<Category>> {
    void showCategoryCards(CategoryClick categoryClick);
}
