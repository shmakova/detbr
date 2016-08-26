package ru.yandex.detbr.developer_settings;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.DeveloperSettingsFragment;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);
}
