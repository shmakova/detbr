package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.CardModule;
import ru.shmakova.detbr.app.di.scopes.PerFragment;
import ru.shmakova.detbr.card.CardFragment;
import ru.shmakova.detbr.card.CardPresenter;

@PerFragment
@Subcomponent(modules = {
        CardModule.class
})
public interface CardComponent {
    void inject(@NonNull CardFragment cardFragment);

    CardPresenter presenter();
}
