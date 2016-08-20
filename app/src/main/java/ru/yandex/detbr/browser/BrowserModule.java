package ru.yandex.detbr.browser;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.presenters.BrowserPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class BrowserModule {
    @Provides
    @NonNull
    public BrowserPresenter provideBrowserPresenter() {
        return new BrowserPresenter();
    }
}
