package ru.shmakova.detbr;

import android.app.Application;
import android.support.annotation.NonNull;

import ru.shmakova.detbr.app.App;
import ru.shmakova.detbr.app.di.components.DaggerApplicationComponent;
import ru.shmakova.detbr.app.di.modules.DeveloperSettingsModule;
import ru.shmakova.detbr.data.developer_settings.DevMetricsProxy;

@SuppressWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public class UnitTestApp extends App {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .developerSettingsModule(new DeveloperSettingsModule() {
                    @NonNull
                    public DevMetricsProxy provideDevMetricsProxy(@NonNull Application application) {
                        return () -> {
                            //No Op
                        };
                    }
                });
    }

    @Override
    protected void setupFabric() {

    }
}
