package ru.yandex.detbr.school;


import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.activities.MainActivity;
import ru.yandex.detbr.ui.fragments.SchoolFragment;

@Module
public class SchoolsDataModule {
    @NonNull
    @Provides
    public SchoolsData provideSchoolsData() {
        return new FakeSchoolsData();
    }
}
