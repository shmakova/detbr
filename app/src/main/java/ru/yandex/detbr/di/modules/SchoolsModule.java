package ru.yandex.detbr.di.modules;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.ui.presenters.SchoolsPresenter;

@Module
public class SchoolsModule {
    @Provides
    @NonNull
    public SchoolsPresenter provideSchoolsPresenter(@NonNull DataRepository dataRepository,
                                                    @NonNull SharedPreferences sharedPreferences) {
        return new SchoolsPresenter(dataRepository, sharedPreferences);
    }
}
