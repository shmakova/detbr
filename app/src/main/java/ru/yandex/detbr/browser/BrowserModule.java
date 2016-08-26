package ru.yandex.detbr.browser;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.presenters.BrowserPresenter;
import ru.yandex.detbr.wot.WotService;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class BrowserModule {
    @Provides
    @NonNull
    public BrowserPresenter provideBrowserPresenter(
            @NonNull BrowserModelImpl browserModel,
            @NonNull WotService wotService) {
        return new BrowserPresenter(browserModel, wotService);
    }

    @Provides
    @NonNull
    public BrowserModel provideBrowserModel(@NonNull BrowserModelImpl browserModelImpl) {
        return browserModelImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public BrowserModelImpl provideBrowserModelImpl() {
        return new BrowserModelImpl();
    }
}
