package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.CardsModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.presentation.presenters.CardsPresenter;
import ru.yandex.detbr.ui.fragments.CardsPagerFragment;

/**
 * Created by shmakova on 21.08.16.
 */

@PerFragment
@Subcomponent(modules = CardsModule.class)
public interface CardsComponent {
    void inject(@NonNull CardsPagerFragment cardsPagerFragment);

    CardsPresenter presenter();
}