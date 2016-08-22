package ru.yandex.detbr;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.detbr.browser.BrowserComponent;
import ru.yandex.detbr.browser.BrowserModule;
import ru.yandex.detbr.developer_settings.DevMetricsProxy;
import ru.yandex.detbr.developer_settings.DeveloperSettingsComponent;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModel;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.developer_settings.LeakCanaryProxy;
import ru.yandex.detbr.schools.SchoolsComponent;
import ru.yandex.detbr.schools.SchoolsModule;
import ru.yandex.detbr.ui.activities.MainActivity;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DeveloperSettingsModule.class,
        BrowserModule.class,
        SchoolsModule.class
})
public interface ApplicationComponent {

    // Provide LeakCanary without injection to leave.
    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    @NonNull
    SchoolsComponent schoolsComponent();

    @NonNull
    DeveloperSettingsComponent plusDeveloperSettingsComponent();

    @NonNull
    BrowserComponent browserComponent();

    DeveloperSettingsModel developerSettingModel();

    DevMetricsProxy devMetricsProxy();

    @NonNull @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    void inject(@NonNull MainActivity mainActivity);
}
