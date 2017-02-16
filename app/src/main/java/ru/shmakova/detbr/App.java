package ru.shmakova.detbr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ru.shmakova.detbr.data.developer_settings.DevMetricsProxy;
import ru.shmakova.detbr.data.developer_settings.DeveloperSettingsModel;
import ru.shmakova.detbr.di.components.ApplicationComponent;
import ru.shmakova.detbr.di.components.DaggerApplicationComponent;
import ru.shmakova.detbr.di.modules.ApplicationModule;
import timber.log.Timber;

public class App extends MultiDexApplication {
    private ApplicationComponent applicationComponent;

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
