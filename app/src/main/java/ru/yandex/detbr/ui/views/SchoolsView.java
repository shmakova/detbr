package ru.yandex.detbr.ui.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by shmakova on 21.08.16.
 */

public interface SchoolsView extends MvpView {
    void setSchoolsData(List<String> schools);
}
