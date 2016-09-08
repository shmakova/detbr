package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.schools.SchoolsRepository;
import ru.yandex.detbr.presentation.presenters.SchoolsPresenter;

@Module
public class SchoolsModule {
    @Provides
    @NonNull
    public SchoolsPresenter provideSchoolsPresenter(@NonNull SchoolsRepository schoolsRepository) {
        return new SchoolsPresenter(schoolsRepository);
    }
}
