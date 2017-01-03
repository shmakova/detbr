package ru.shmakova.detbr.di.components;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.shmakova.detbr.data.developer_settings.DevMetricsProxy;
import ru.shmakova.detbr.data.developer_settings.DeveloperSettingsModel;
import ru.shmakova.detbr.data.developer_settings.LeakCanaryProxy;
import ru.shmakova.detbr.di.modules.ApplicationModule;
import ru.shmakova.detbr.di.modules.BrowserModule;
import ru.shmakova.detbr.di.modules.CardModule;
import ru.shmakova.detbr.di.modules.CardsModule;
import ru.shmakova.detbr.di.modules.CategoriesModule;
import ru.shmakova.detbr.di.modules.DbModule;
import ru.shmakova.detbr.di.modules.DeveloperSettingsModule;
import ru.shmakova.detbr.di.modules.FavoritesModule;
import ru.shmakova.detbr.di.modules.FirebaseModule;
import ru.shmakova.detbr.di.modules.MainModule;
import ru.shmakova.detbr.di.modules.NavigationModule;
import ru.shmakova.detbr.di.modules.SchoolsModule;
import ru.shmakova.detbr.di.modules.TabsModule;
import ru.shmakova.detbr.di.modules.WotNetworkModule;
import ru.shmakova.detbr.ui.activities.MainActivity;

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
    FavoritesComponent plus(FavoritesModule module, NavigationModule navigationModule);

    @NonNull
    CategoriesComponent plus(CategoriesModule module);

    @NonNull
    TabsComponent plus(TabsModule tabsModule, NavigationModule navigationModule);

    @NonNull
    MainComponent plus(MainModule mainModule, NavigationModule navigationModule);

    DeveloperSettingsModel developerSettingModel();

    DevMetricsProxy devMetricsProxy();

    @NonNull
    @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    void inject(@NonNull MainActivity mainActivity);
}
