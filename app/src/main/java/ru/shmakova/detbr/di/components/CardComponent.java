package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.CardModule;
import ru.shmakova.detbr.di.scopes.PerFragment;
import ru.shmakova.detbr.presentation.presenters.CardPresenter;
import ru.shmakova.detbr.ui.fragments.CardFragment;

/**
 * Created by shmakova on 04.09.16.
 */

@PerFragment
@Subcomponent(modules = {
        CardModule.class
})
public interface CardComponent {
    void inject(@NonNull CardFragment cardFragment);

    CardPresenter presenter();
}