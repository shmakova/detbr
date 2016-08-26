package ru.yandex.detbr;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import ru.yandex.detbr.developer_settings.DevMetricsProxy;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModel;
import timber.log.Timber;

public class App extends Application {
    private ApplicationComponent applicationComponent;
    private Tracker tracker;

    public Tracker getDefaultTracker() {
        synchronized (this) {
            if (tracker == null) {
                GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
                // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
                tracker = analytics.newTracker(R.xml.global_tracker);
            }
            return tracker;
        }
    }

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupFabric();
        applicationComponent = prepareApplicationComponent().build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            developerSettingModel.apply();

            DevMetricsProxy devMetricsProxy = applicationComponent.devMetricsProxy();
            devMetricsProxy.apply();
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

    protected void setupFabric() {
        Fabric.with(this, new Crashlytics());
    }
}
