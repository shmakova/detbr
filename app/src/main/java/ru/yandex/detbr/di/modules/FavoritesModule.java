package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.ui.presenters.FavoritesPresenter;

/**
 * Created by shmakova on 28.08.16.
 */

@Module
public class FavoritesModule {
    @Provides
    @NonNull
    public FavoritesPresenter provideFavoritesPresenter(@NonNull DataRepository dataRepository) {
        return new FavoritesPresenter(dataRepository);
    }
}