package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.tabs.FakeTabsRepository;
import ru.yandex.detbr.data.tabs.TabsRepository;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.ui.adapters.TabsAdapter;
import ru.yandex.detbr.ui.managers.NavigationManager;
import ru.yandex.detbr.ui.presenters.TabsPresenter;

/**
 * Created by shmakova on 29.08.16.
 */

@Module
public class TabsModule {
    @Provides
    @NonNull
    public TabsPresenter provideTabsPresenter(@NonNull TabsRepository tabsRepository,
                                              @NonNull NavigationManager navigationManager) {
        return new TabsPresenter(tabsRepository, navigationManager);
    }

    @Provides
    @NonNull
    @PerFragment
    public TabsRepository provideTabsRepository() {
        return new FakeTabsRepository();
    }

    @Provides
    @NonNull
    @PerFragment
    public TabsAdapter provideTabsAdapter() {
        return new TabsAdapter();
    }
}