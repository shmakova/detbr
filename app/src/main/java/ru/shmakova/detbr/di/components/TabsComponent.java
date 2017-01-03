package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.NavigationModule;
import ru.shmakova.detbr.di.modules.TabsModule;
import ru.shmakova.detbr.di.scopes.PerFragment;
import ru.shmakova.detbr.presentation.presenters.TabsPresenter;
import ru.shmakova.detbr.ui.fragments.TabsFragment;

/**
 * Created by shmakova on 29.08.16.
 */

@PerFragment
@Subcomponent(modules = {
        TabsModule.class,
        NavigationModule.class
})
public interface TabsComponent {
    void inject(@NonNull TabsFragment tabsFragment);

    @NonNull
    TabsPresenter presenter();
}
