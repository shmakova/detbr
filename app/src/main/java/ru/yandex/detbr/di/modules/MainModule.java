package ru.yandex.detbr.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.presentation.presenters.MainPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class MainModule {
    @Provides
    @NonNull
    MainPresenter provideMainPresenter(@NonNull SharedPreferences sharedPreferences,
                                       @NonNull NavigationManager navigationManager,
                                       @NonNull DataRepository dataRepository) {
        return new MainPresenter(sharedPreferences, navigationManager, dataRepository);
    }
}
