package ru.yandex.detbr;

import android.app.Application;
import android.support.annotation.NonNull;

import ru.yandex.detbr.data.developer_settings.DevMetricsProxy;
import ru.yandex.detbr.di.components.DaggerApplicationComponent;
import ru.yandex.detbr.di.modules.DeveloperSettingsModule;

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
