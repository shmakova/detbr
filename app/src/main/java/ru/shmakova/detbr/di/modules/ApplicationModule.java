package ru.shmakova.detbr.di.modules;

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
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.data.cards.CardsRepositoryImpl;
import ru.shmakova.detbr.data.categories.CategoriesRepository;
import ru.shmakova.detbr.data.categories.CategoriesRepositoryImpl;
import ru.shmakova.detbr.data.schools.SchoolsRepository;
import ru.shmakova.detbr.data.schools.SchoolsRepositoryImpl;
import ru.shmakova.detbr.data.stopwords.StopWordsRepository;
import ru.shmakova.detbr.data.stopwords.StopWordsRepositoryImpl;
import ru.shmakova.detbr.data.tabs.TabsRepository;
import ru.shmakova.detbr.data.tabs.TabsRepositoryImpl;
import ru.shmakova.detbr.managers.TabsManager;
import ru.shmakova.detbr.utils.ErrorMessageDeterminer;

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
    public TabsRepository provideTabsRepository(StorIOSQLite storIOSQLite) {
        return new TabsRepositoryImpl(storIOSQLite);
    }

    @Provides
    @Singleton
    public CardsRepository provideCardsRepository(StorIOSQLite storIOSQLite, DatabaseReference databaseReference) {
        return new CardsRepositoryImpl(storIOSQLite, databaseReference);
    }

    @Provides
    @Singleton
    public SchoolsRepository provideSchoolsRepository(SharedPreferences sharedPreferences,
                                                      DatabaseReference databaseReference) {
        return new SchoolsRepositoryImpl(sharedPreferences, databaseReference);
    }

    @Provides
    @Singleton
    public CategoriesRepository provideCategoriesRepository(DatabaseReference databaseReference) {
        return new CategoriesRepositoryImpl(databaseReference);
    }

    @Provides
    @Singleton
    public StopWordsRepository provideStopWordsRepository(DatabaseReference databaseReference) {
        return new StopWordsRepositoryImpl();
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
