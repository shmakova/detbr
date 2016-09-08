package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.managers.NavigationManager;
import ru.yandex.detbr.ui.managers.TabsManager;
import ru.yandex.detbr.ui.presenters.TabsPresenter;

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