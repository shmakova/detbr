package ru.yandex.detbr.ui.presenters;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.ui.views.SchoolsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shmakova on 21.08.16.
 */

public class SchoolsPresenter extends MvpBasePresenter<SchoolsView> {
    @NonNull
    private final DataRepository dataRepository;
    @NonNull
    private final SharedPreferences sharedPreferences;

    public SchoolsPresenter(@NonNull DataRepository dataRepository,
                            @NonNull SharedPreferences sharedPreferences) {
        this.dataRepository = dataRepository;
        this.sharedPreferences = sharedPreferences;
    }

    public void loadSchools() {
        final SchoolsView view = getView();

        if (isViewAttached()) {
            dataRepository.getSchoolsList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::setSchoolsData);
        }
    }

    public void saveSchool(String school) {
        sharedPreferences
                .edit()
                .putString(DataRepository.SCHOOL_TAG, school)
                .apply();
    }
}
