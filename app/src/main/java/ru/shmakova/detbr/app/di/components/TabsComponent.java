package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.NavigationModule;
import ru.shmakova.detbr.app.di.modules.TabsModule;
import ru.shmakova.detbr.app.di.scopes.PerFragment;
import ru.shmakova.detbr.tabs.TabsFragment;
import ru.shmakova.detbr.tabs.TabsPresenter;

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
