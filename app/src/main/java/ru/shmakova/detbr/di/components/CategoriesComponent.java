package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.CategoriesModule;
import ru.shmakova.detbr.di.scopes.PerFragment;
import ru.shmakova.detbr.presentation.presenters.CategoriesPresenter;
import ru.shmakova.detbr.ui.fragments.CategoriesFragment;

/**
 * Created by shmakova on 24.08.16.
 */

@PerFragment
@Subcomponent(modules = CategoriesModule.class)
public interface CategoriesComponent {
    void inject(@NonNull CategoriesFragment categoriesFragment);

    @NonNull
    CategoriesPresenter presenter();
}
