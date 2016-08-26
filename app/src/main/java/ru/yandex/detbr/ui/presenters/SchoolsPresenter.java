package ru.yandex.detbr.ui.presenters;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import ru.yandex.detbr.schools.SchoolsModel;
import ru.yandex.detbr.ui.views.SchoolsView;

/**
 * Created by shmakova on 21.08.16.
 */

public class SchoolsPresenter extends Presenter<SchoolsView> {
    @NonNull
    private final SchoolsModel schoolsModel;
    @NonNull
    private final SharedPreferences sharedPreferences;

    public SchoolsPresenter(@NonNull SchoolsModel schoolsModel,
                            @NonNull SharedPreferences sharedPreferences) {
        this.schoolsModel = schoolsModel;
        this.sharedPreferences = sharedPreferences;
    }

    public void loadSchools() {
        final SchoolsView view = view();

        if (view != null) {
            view.setSchoolsData(schoolsModel.getSchoolsList());
        }
    }

    public void saveSchool(String school) {
        sharedPreferences
                .edit()
                .putString(SchoolsModel.SCHOOL_TAG, school)
                .apply();
    }
}
