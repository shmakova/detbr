package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import ru.yandex.detbr.categories.CategoriesModel;
import ru.yandex.detbr.ui.views.CategoriesView;

/**
 * Created by shmakova on 24.08.16.
 */

public class CategoriesPresenter extends Presenter<CategoriesView> {
    @NonNull
    private final CategoriesModel categoriesModel;

    public CategoriesPresenter(@NonNull CategoriesModel categoriesModel) {
        this.categoriesModel = categoriesModel;
    }

    public void loadCategories() {
        final CategoriesView view = view();

        if (view != null) {
            view.setCategories(categoriesModel.getCategories());
        }
    }
}
