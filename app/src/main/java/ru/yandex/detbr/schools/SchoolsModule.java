package ru.yandex.detbr.schools;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.presenters.SchoolsPresenter;

@Module
public class SchoolsModule {
    @NonNull
    @Provides
    @Singleton
    public SchoolsModel provideSchoolsRepository() {
        return new SchoolsModelImpl();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @NonNull
    public SchoolsPresenter provideSchoolsPresenter(@NonNull SchoolsModel schoolsModel,
                                                    @NonNull SharedPreferences sharedPreferences) {
        return new SchoolsPresenter(schoolsModel, sharedPreferences);
    }
}
