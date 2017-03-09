package ru.shmakova.detbr.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.categories.CategoriesPresenter;
import ru.shmakova.detbr.data.categories.CategoriesRepository;

@Module
public class CategoriesModule {
    @Provides
    @NonNull
    public CategoriesPresenter provideCategoriesPresenter(@NonNull CategoriesRepository categoriesRepository) {
        return new CategoriesPresenter(categoriesRepository);
    }
}
