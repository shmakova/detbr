package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.NavigationModule;
import ru.yandex.detbr.di.modules.TabsModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.ui.fragments.TabsFragment;
import ru.yandex.detbr.ui.presenters.TabsPresenter;

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
