package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.presenters.MainPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class MainModule {
    @Provides
    @NonNull
    public MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }
}
