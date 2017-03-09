package ru.shmakova.detbr.developer_settings;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpView;

import ru.shmakova.detbr.ui.other.AnyThread;


public interface DeveloperSettingsView extends MvpView {

    @AnyThread
    void changeBuildVersionCode(@NonNull String versionCode);

    @AnyThread
    void changeBuildVersionName(@NonNull String versionName);

    @AnyThread
    void changeStethoState(boolean enabled);

    @AnyThread
    void changeLeakCanaryState(boolean enabled);

    @AnyThread
    void changeTinyDancerState(boolean enabled);

    @AnyThread
    void showMessage(@NonNull String message);

    @AnyThread
    void showAppNeedsToBeRestarted();
}
