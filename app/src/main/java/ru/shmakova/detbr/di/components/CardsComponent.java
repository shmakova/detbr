package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.CardsModule;
import ru.shmakova.detbr.di.scopes.PerFragment;
import ru.shmakova.detbr.presentation.presenters.CardsPresenter;
import ru.shmakova.detbr.ui.fragments.CardsPagerFragment;

/**
 * Created by shmakova on 21.08.16.
 */

@PerFragment
@Subcomponent(modules = CardsModule.class)
public interface CardsComponent {
    void inject(@NonNull CardsPagerFragment cardsPagerFragment);

    CardsPresenter presenter();
}