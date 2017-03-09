package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.FavoritesModule;
import ru.shmakova.detbr.app.di.modules.NavigationModule;
import ru.shmakova.detbr.app.di.scopes.PerFragment;
import ru.shmakova.detbr.favorites.FavoritesFragment;
import ru.shmakova.detbr.favorites.FavoritesPresenter;

@PerFragment
@Subcomponent(modules = {
        FavoritesModule.class,
        NavigationModule.class
})
public interface FavoritesComponent {
    void inject(@NonNull FavoritesFragment favoritesFragment);

    FavoritesPresenter presenter();
}
