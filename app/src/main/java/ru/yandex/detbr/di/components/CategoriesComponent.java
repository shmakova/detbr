package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.CategoriesModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.ui.adapters.CategoriesAdapter;
import ru.yandex.detbr.ui.fragments.CategoriesFragment;
import ru.yandex.detbr.ui.presenters.CategoriesPresenter;

/**
 * Created by shmakova on 24.08.16.
 */

@PerFragment
@Subcomponent(modules = CategoriesModule.class)
public interface CategoriesComponent {
    void inject(@NonNull CategoriesFragment categoriesFragment);

    CategoriesPresenter presenter();

    CategoriesAdapter adapter();
}
