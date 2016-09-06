package ru.yandex.detbr.presentation.presenters;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.presentation.views.SchoolsView;
import rx.Observable;

/**
 * Created by shmakova on 21.08.16.
 */

public class SchoolsPresenter extends BaseRxPresenter<SchoolsView, List<String>> {
    @NonNull
    private final DataRepository dataRepository;
    @NonNull
    private final SharedPreferences sharedPreferences;

    public SchoolsPresenter(@NonNull DataRepository dataRepository,
                            @NonNull SharedPreferences sharedPreferences) {
        this.dataRepository = dataRepository;
        this.sharedPreferences = sharedPreferences;
    }

    public void loadSchools(boolean pullToRefresh) {
        Observable<List<String>> observable = dataRepository.getSchoolsList();
        subscribe(observable, pullToRefresh);
    }

    public void saveSchool(String school) {
        sharedPreferences
                .edit()
                .putString(DataRepository.SCHOOL_TAG, school)
                .apply();
    }
}
