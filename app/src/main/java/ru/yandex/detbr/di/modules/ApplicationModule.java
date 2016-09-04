package ru.yandex.detbr.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.database.DatabaseReference;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.FakeDataRepository;
import ru.yandex.detbr.data.tabs.FakeTabsRepository;
import ru.yandex.detbr.data.tabs.TabsRepository;
import ru.yandex.detbr.ui.managers.LikeManager;
import ru.yandex.detbr.ui.managers.TabsManager;
import ru.yandex.detbr.utils.ErrorMessageDeterminer;

@Module
public class ApplicationModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

    @NonNull
    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    DataRepository providesDataRepository(StorIOSQLite storIOSQLite, DatabaseReference databaseReference) {
        return new FakeDataRepository(storIOSQLite, databaseReference);
    }

    @Provides
    @Singleton
    Tracker providesTracker(Application application) {
        synchronized (this) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(application);
            return analytics.newTracker(R.xml.global_tracker);
        }
    }

    @Provides
    @Singleton
    public ErrorMessageDeterminer providesErrorMessageDeterminer() {
        return new ErrorMessageDeterminer();
    }

    @Provides
    @Singleton
    public TabsManager providesTabsManager(TabsRepository tabsRepository) {
        return new TabsManager(tabsRepository);
    }

    @Provides
    @Singleton
    public LikeManager providesLikeManager(DataRepository dataRepository) {
        return new LikeManager(dataRepository);
    }

    @Provides
    @Singleton
    public TabsRepository provideTabsRepository() {
        return new FakeTabsRepository();
    }

    @Provides
    @NonNull
    @Singleton
    public Application provideApp() {
        return application;
    }

    @Provides
    @NonNull
    @Named(MAIN_THREAD_HANDLER)
    @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

}
