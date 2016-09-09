package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.CardModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.presentation.presenters.CardPresenter;
import ru.yandex.detbr.ui.fragments.CardFragment;

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