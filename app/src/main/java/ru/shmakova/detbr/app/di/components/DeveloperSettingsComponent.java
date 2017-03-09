package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.developer_settings.DeveloperSettingsFragment;
import ru.shmakova.detbr.developer_settings.DeveloperSettingsPresenter;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);

    DeveloperSettingsPresenter presenter();
}
