package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.CategoriesModule;
import ru.shmakova.detbr.app.di.scopes.PerFragment;
import ru.shmakova.detbr.categories.CategoriesFragment;
import ru.shmakova.detbr.categories.CategoriesPresenter;

@PerFragment
@Subcomponent(modules = CategoriesModule.class)
public interface CategoriesComponent {
    void inject(@NonNull CategoriesFragment categoriesFragment);

    @NonNull
    CategoriesPresenter presenter();
}
