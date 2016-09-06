package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.managers.TabsManager;
import ru.yandex.detbr.presentation.presenters.TabsPresenter;
import ru.yandex.detbr.ui.adapters.TabsAdapter;

/**
 * Created by shmakova on 29.08.16.
 */

@Module
public class TabsModule {
    @Provides
    @NonNull
    public TabsPresenter provideTabsPresenter(@NonNull NavigationManager navigationManager,
                                              @NonNull TabsManager tabsManager) {
        return new TabsPresenter(navigationManager, tabsManager);
    }

    @Provides
    @NonNull
    @PerFragment
    public TabsAdapter provideTabsAdapter() {
        return new TabsAdapter();
    }
}