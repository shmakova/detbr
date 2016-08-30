package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.CardsModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.ui.fragments.BaseCardsPagerFragment;
import ru.yandex.detbr.ui.presenters.CardsPresenter;

/**
 * Created by shmakova on 21.08.16.
 */

@PerFragment
@Subcomponent(modules = CardsModule.class)
public interface CardsComponent {
    void inject(@NonNull BaseCardsPagerFragment baseCardsPagerFragment);

    CardsPresenter presenter();
}