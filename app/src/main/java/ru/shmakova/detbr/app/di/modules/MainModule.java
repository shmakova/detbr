package ru.shmakova.detbr.app.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.app.NavigationManager;
import ru.shmakova.detbr.main.MainPresenter;

@Module
public class MainModule {
    @Provides
    @NonNull
    MainPresenter provideMainPresenter(@NonNull NavigationManager navigationManager,
                                       @NonNull SharedPreferences sharedPreferences) {
        return new MainPresenter(navigationManager, sharedPreferences);
    }
}
