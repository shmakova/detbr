package ru.shmakova.detbr.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.app.NavigationManager;
import ru.shmakova.detbr.tabs.TabsManager;
import ru.shmakova.detbr.tabs.TabsPresenter;

@Module
public class TabsModule {
    @Provides
    @NonNull
    public TabsPresenter provideTabsPresenter(@NonNull NavigationManager navigationManager,
                                              @NonNull TabsManager tabsManager) {
        return new TabsPresenter(navigationManager, tabsManager);
    }
}
