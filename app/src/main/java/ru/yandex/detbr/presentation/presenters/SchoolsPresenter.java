package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.schools.SchoolsRepository;
import ru.yandex.detbr.presentation.views.SchoolsView;
import rx.Observable;

/**
 * Created by shmakova on 21.08.16.
 */

public class SchoolsPresenter extends BaseRxPresenter<SchoolsView, List<String>> {
    @NonNull
    private final SchoolsRepository schoolsRepository;

    public SchoolsPresenter(@NonNull SchoolsRepository schoolsRepository) {
        this.schoolsRepository = schoolsRepository;
    }

    public void loadSchools(boolean pullToRefresh) {
        Observable<List<String>> observable = schoolsRepository.getSchoolsList();
        subscribe(observable, pullToRefresh);
    }

    public void saveSchool(String school) {
        schoolsRepository.saveSchool(school);
    }
}
