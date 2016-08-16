package ru.yandex.detbr.developer_settings;

import android.support.annotation.NonNull;

import ru.yandex.detbr.ui.fragments.DeveloperSettingsFragment;

import dagger.Subcomponent;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);
}
