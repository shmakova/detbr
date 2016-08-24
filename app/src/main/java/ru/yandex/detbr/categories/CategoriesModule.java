package ru.yandex.detbr.categories;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.presenters.CategoriesPresenter;

/**
 * Created by shmakova on 24.08.16.
 */

@Module
public class CategoriesModule {
    @Provides
    @NonNull
    public CategoriesPresenter provideCardsPresenter(@NonNull CategoriesModel categoriesModel) {
        return new CategoriesPresenter(categoriesModel);
    }

    @Provides
    @NonNull
    public CategoriesModel provideCardsModel(@NonNull CategoriesModelImpl categoriesModelImpl) {
        return categoriesModelImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public CategoriesModelImpl provideCardsModelImpl() {
        return new CategoriesModelImpl();
    }
}
