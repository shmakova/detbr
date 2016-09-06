package ru.yandex.detbr.di.components;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.detbr.data.developer_settings.DevMetricsProxy;
import ru.yandex.detbr.data.developer_settings.DeveloperSettingsModel;
import ru.yandex.detbr.data.developer_settings.LeakCanaryProxy;
import ru.yandex.detbr.di.modules.ApplicationModule;
import ru.yandex.detbr.di.modules.BrowserModule;
import ru.yandex.detbr.di.modules.CardModule;
import ru.yandex.detbr.di.modules.CardsModule;
import ru.yandex.detbr.di.modules.CategoriesModule;
import ru.yandex.detbr.di.modules.DbModule;
import ru.yandex.detbr.di.modules.DeveloperSettingsModule;
import ru.yandex.detbr.di.modules.FavoritesModule;
import ru.yandex.detbr.di.modules.FirebaseModule;
import ru.yandex.detbr.di.modules.MainModule;
import ru.yandex.detbr.di.modules.NavigationModule;
import ru.yandex.detbr.di.modules.SchoolsModule;
import ru.yandex.detbr.di.modules.TabsModule;
import ru.yandex.detbr.di.modules.WotNetworkModule;
import ru.yandex.detbr.ui.activities.MainActivity;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DeveloperSettingsModule.class,
        WotNetworkModule.class,
        FirebaseModule.class,
        DbModule.class
})
public interface ApplicationComponent {

    // Provide LeakCanary without injection to leave.
    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    @NonNull
    SchoolsComponent plus(SchoolsModule module);

    @NonNull
    DeveloperSettingsComponent plusDeveloperSettingsComponent();

    @NonNull
    BrowserComponent plus(BrowserModule module);

    @NonNull
    CardsComponent plus(CardsModule module);

    @NonNull
    CardComponent plus(CardModule module);

    @NonNull
    FavoritesComponent plus(FavoritesModule module);

    @NonNull
    CategoriesComponent plus(CategoriesModule module);

    @NonNull
    TabsComponent plus(TabsModule module, NavigationModule navigationModule);

    @NonNull
    MainComponent plus(MainModule mainModule, NavigationModule navigationModule);

    DeveloperSettingsModel developerSettingModel();

    DevMetricsProxy devMetricsProxy();

    @NonNull
    @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    void inject(@NonNull MainActivity mainActivity);
}
