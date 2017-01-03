package ru.shmakova.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.managers.NavigationManager;
import ru.shmakova.detbr.managers.TabsManager;
import ru.shmakova.detbr.presentation.presenters.TabsPresenter;

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
}