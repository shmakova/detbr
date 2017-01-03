package ru.shmakova.detbr.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.managers.NavigationManager;
import ru.shmakova.detbr.presentation.presenters.MainPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class MainModule {
    @Provides
    @NonNull
    MainPresenter provideMainPresenter(@NonNull NavigationManager navigationManager,
                                       @NonNull SharedPreferences sharedPreferences) {
        return new MainPresenter(navigationManager, sharedPreferences);
    }
}
