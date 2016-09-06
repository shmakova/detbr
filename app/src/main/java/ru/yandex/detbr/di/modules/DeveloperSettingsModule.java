package ru.yandex.detbr.di.modules;

import android.app.Application;
import android.support.annotation.NonNull;

import com.github.pedrovgs.lynx.LynxConfig;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.developer_settings.DevMetricsProxy;
import ru.yandex.detbr.data.developer_settings.DevMetricsProxyImpl;
import ru.yandex.detbr.data.developer_settings.DeveloperSettings;
import ru.yandex.detbr.data.developer_settings.DeveloperSettingsModel;
import ru.yandex.detbr.data.developer_settings.DeveloperSettingsModelImpl;
import ru.yandex.detbr.data.developer_settings.LeakCanaryProxy;
import ru.yandex.detbr.data.developer_settings.LeakCanaryProxyImpl;
import ru.yandex.detbr.data.developer_settings.MainActivityViewModifier;
import ru.yandex.detbr.presentation.presenters.DeveloperSettingsPresenter;
import ru.yandex.detbr.ui.other.ViewModifier;

import static android.content.Context.MODE_PRIVATE;

@Module
public class DeveloperSettingsModule {

    @NonNull
    public static final String MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier";

    @Provides
    @NonNull
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    public ViewModifier provideMainActivityViewModifier() {
        return new MainActivityViewModifier();
    }

    @Provides
    @NonNull
    public DeveloperSettingsModel provideDeveloperSettingsModel(@NonNull DeveloperSettingsModelImpl developerSettingsModelImpl) {
        return developerSettingsModelImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public DeveloperSettings provideDeveloperSettings(@NonNull Application application) {
        return new DeveloperSettings(application.getSharedPreferences("developer_settings", MODE_PRIVATE));
    }

    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy(@NonNull Application application) {
        return new LeakCanaryProxyImpl(application);
    }

    // We will use this concrete type for debug code, but main code will see only DeveloperSettingsModel interface.
    @Provides
    @NonNull
    @Singleton
    public DeveloperSettingsModelImpl provideDeveloperSettingsModelImpl(@NonNull Application application,
                                                                        @NonNull DeveloperSettings developerSettings,
                                                                        @NonNull LeakCanaryProxy leakCanaryProxy) {
        return new DeveloperSettingsModelImpl(application, developerSettings, leakCanaryProxy);
    }

    @Provides
    @NonNull
    public DeveloperSettingsPresenter provideDeveloperSettingsPresenter(@NonNull DeveloperSettingsModelImpl developerSettingsModelImpl) {
        return new DeveloperSettingsPresenter(developerSettingsModelImpl);
    }

    @NonNull
    @Provides
    public LynxConfig provideLynxConfig() {
        return new LynxConfig();
    }

    @NonNull
    @Provides
    @Singleton
    public DevMetricsProxy provideDevMetricsProxy(@NonNull Application application) {
        return new DevMetricsProxyImpl(application);
    }
}
