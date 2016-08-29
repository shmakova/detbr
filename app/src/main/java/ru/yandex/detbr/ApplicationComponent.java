package ru.yandex.detbr;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.detbr.browser.BrowserComponent;
import ru.yandex.detbr.browser.BrowserModule;
import ru.yandex.detbr.cards.CardsComponent;
import ru.yandex.detbr.cards.CardsModule;
import ru.yandex.detbr.categories.CategoriesComponent;
import ru.yandex.detbr.categories.CategoriesModule;
import ru.yandex.detbr.db.DbModule;
import ru.yandex.detbr.developer_settings.DevMetricsProxy;
import ru.yandex.detbr.developer_settings.DeveloperSettingsComponent;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModel;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.developer_settings.LeakCanaryProxy;
import ru.yandex.detbr.schools.SchoolsComponent;
import ru.yandex.detbr.schools.SchoolsModule;
import ru.yandex.detbr.ui.activities.MainActivity;
import ru.yandex.detbr.wot.WotModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DeveloperSettingsModule.class,
        BrowserModule.class,
        WotModule.class,
        CardsModule.class,
        SchoolsModule.class,
        CategoriesModule.class,
        DbModule.class
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

    @NonNull
    CardsComponent cardsComponent();

    @NonNull
    CategoriesComponent categoriesComponent();

    DeveloperSettingsModel developerSettingModel();

    DevMetricsProxy devMetricsProxy();

    @NonNull @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    void inject(@NonNull MainActivity mainActivity);
}
