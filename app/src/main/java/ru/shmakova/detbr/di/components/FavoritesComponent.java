package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.FavoritesModule;
import ru.shmakova.detbr.di.modules.NavigationModule;
import ru.shmakova.detbr.di.scopes.PerFragment;
import ru.shmakova.detbr.presentation.presenters.FavoritesPresenter;
import ru.shmakova.detbr.ui.fragments.FavoritesFragment;

/**
 * Created by shmakova on 28.08.16.
 */

@PerFragment
@Subcomponent(modules = {
        FavoritesModule.class,
        NavigationModule.class
})
public interface FavoritesComponent {
    void inject(@NonNull FavoritesFragment favoritesFragment);

    FavoritesPresenter presenter();
}