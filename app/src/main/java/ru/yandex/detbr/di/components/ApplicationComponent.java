package ru.yandex.detbr.di.components;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.detbr.di.modules.ApplicationModule;
import ru.yandex.detbr.di.modules.BrowserModule;
import ru.yandex.detbr.di.modules.CardModule;
import ru.yandex.detbr.di.modules.CardsModule;
import ru.yandex.detbr.di.modules.CategoriesModule;
import ru.yandex.detbr.di.modules.DbModule;
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
        WotNetworkModule.class,
        FirebaseModule.class,
        DbModule.class
})
public interface ApplicationComponent {

    @NonNull
    SchoolsComponent plus(SchoolsModule module);

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

    @NonNull
    @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    void inject(@NonNull MainActivity mainActivity);
}
