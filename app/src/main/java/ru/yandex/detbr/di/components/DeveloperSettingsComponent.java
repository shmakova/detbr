package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.DeveloperSettingsFragment;
import ru.yandex.detbr.ui.presenters.DeveloperSettingsPresenter;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);

    DeveloperSettingsPresenter presenter();
}
