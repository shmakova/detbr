package ru.yandex.detbr.ui.presenters;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import ru.yandex.detbr.schools.SchoolsRepository;
import ru.yandex.detbr.ui.views.SchoolsView;

/**
 * Created by shmakova on 21.08.16.
 */

public class SchoolsPresenter extends Presenter<SchoolsView> {
    @NonNull
    private final SchoolsRepository schoolsRepository;
    @NonNull
    private final SharedPreferences sharedPreferences;

    public SchoolsPresenter(@NonNull SchoolsRepository schoolsRepository,
                            @NonNull SharedPreferences sharedPreferences) {
        this.schoolsRepository = schoolsRepository;
        this.sharedPreferences = sharedPreferences;
    }

    public void loadSchools() {
        final SchoolsView view = view();

        if (view != null) {
            view.setSchoolsData(schoolsRepository.getSchoolsList());
        }
    }

    public void saveSchool(String school) {
        sharedPreferences
                .edit()
                .putString(SchoolsRepository.SCHOOL_TAG, school)
                .apply();
    }
}
