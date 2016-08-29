package ru.yandex.detbr.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.managers.NavigationManager;
import ru.yandex.detbr.ui.presenters.MainPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class MainModule {
    @Provides
    @NonNull
    MainPresenter provideMainPresenter(@NonNull SharedPreferences sharedPreferences,
                                       @NonNull NavigationManager navigationManager) {
        return new MainPresenter(sharedPreferences, navigationManager);
    }
}
