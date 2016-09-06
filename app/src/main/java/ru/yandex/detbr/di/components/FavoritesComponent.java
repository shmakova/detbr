package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.FavoritesModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.presentation.presenters.FavoritesPresenter;
import ru.yandex.detbr.ui.fragments.FavoritesFragment;

/**
 * Created by shmakova on 28.08.16.
 */

@PerFragment
@Subcomponent(modules = FavoritesModule.class)
public interface FavoritesComponent {
    void inject(@NonNull FavoritesFragment favoritesFragment);

    FavoritesPresenter presenter();
}