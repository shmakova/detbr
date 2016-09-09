package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.categories.CategoriesRepository;
import ru.yandex.detbr.presentation.presenters.CategoriesPresenter;

/**
 * Created by shmakova on 24.08.16.
 */

@Module
public class CategoriesModule {
    @Provides
    @NonNull
    public CategoriesPresenter provideCategoriesPresenter(@NonNull CategoriesRepository categoriesRepository) {
        return new CategoriesPresenter(categoriesRepository);
    }
}
