package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.wot_network.WotService;
import ru.yandex.detbr.ui.managers.TabsManager;
import ru.yandex.detbr.ui.presenters.BrowserPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class BrowserModule {
    @Provides
    @NonNull
    public BrowserPresenter provideBrowserPresenter(@NonNull WotService wotService,
                                                    @NonNull TabsManager tabsManager) {
        return new BrowserPresenter(wotService, tabsManager);
    }
}
