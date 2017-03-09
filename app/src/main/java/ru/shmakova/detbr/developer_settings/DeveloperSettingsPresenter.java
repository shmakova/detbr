package ru.shmakova.detbr.developer_settings;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.shmakova.detbr.data.developer_settings.DeveloperSettingsModelImpl;

public class DeveloperSettingsPresenter extends MvpBasePresenter<DeveloperSettingsView> {

    @NonNull
    private final DeveloperSettingsModelImpl developerSettingsModel;

    public DeveloperSettingsPresenter(@NonNull DeveloperSettingsModelImpl developerSettingsModel) {
        this.developerSettingsModel = developerSettingsModel;
    }

    @Override
    public void attachView(DeveloperSettingsView view) {
        super.attachView(view);
        view.changeBuildVersionCode(developerSettingsModel.getBuildVersionCode());
        view.changeBuildVersionName(developerSettingsModel.getBuildVersionName());
        view.changeStethoState(developerSettingsModel.isStethoEnabled());
        view.changeLeakCanaryState(developerSettingsModel.isLeakCanaryEnabled());
        view.changeTinyDancerState(developerSettingsModel.isTinyDancerEnabled());

    }

    public void changeStethoState(boolean enabled) {
        if (developerSettingsModel.isStethoEnabled() == enabled) {
            return; // no-op
        }

        boolean stethoWasEnabled = developerSettingsModel.isStethoEnabled();

        developerSettingsModel.changeStethoState(enabled);

        final DeveloperSettingsView view = getView();

        if (isViewAttached()) {
            view.showMessage("Stetho was " + booleanToEnabledDisabled(enabled));

            if (stethoWasEnabled) {
                view.showAppNeedsToBeRestarted();
            }
        }
    }

    public void changeLeakCanaryState(boolean enabled) {
        if (developerSettingsModel.isLeakCanaryEnabled() == enabled) {
            return; // no-op
        }

        developerSettingsModel.changeLeakCanaryState(enabled);

        final DeveloperSettingsView view = getView();

        if (isViewAttached()) {
            view.showMessage("LeakCanary was " + booleanToEnabledDisabled(enabled));
            view.showAppNeedsToBeRestarted(); // LeakCanary can not be enabled on demand (or it's possible?)
        }
    }

    public void changeTinyDancerState(boolean enabled) {
        if (developerSettingsModel.isTinyDancerEnabled() == enabled) {
            return; // no-op
        }

        developerSettingsModel.changeTinyDancerState(enabled);

        final DeveloperSettingsView view = getView();

        if (isViewAttached()) {
            view.showMessage("TinyDancer was " + booleanToEnabledDisabled(enabled));
        }
    }

    @NonNull
    private static String booleanToEnabledDisabled(boolean enabled) {
        return enabled ? "enabled" : "disabled";
    }
}
