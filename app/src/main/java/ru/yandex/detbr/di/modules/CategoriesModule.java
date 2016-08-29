package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.ui.presenters.CategoriesPresenter;

/**
 * Created by shmakova on 24.08.16.
 */

@Module
public class CategoriesModule {
    @Provides
    @NonNull
    public CategoriesPresenter provideCategoriesPresenter(@NonNull DataRepository dataRepository) {
        return new CategoriesPresenter(dataRepository);
    }
}
