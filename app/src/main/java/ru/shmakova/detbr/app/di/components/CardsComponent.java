package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.CardsModule;
import ru.shmakova.detbr.app.di.scopes.PerFragment;
import ru.shmakova.detbr.cards.CardsPagerFragment;
import ru.shmakova.detbr.cards.CardsPresenter;

@PerFragment
@Subcomponent(modules = CardsModule.class)
public interface CardsComponent {
    void inject(@NonNull CardsPagerFragment cardsPagerFragment);

    CardsPresenter presenter();
}