package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.presentation.presenters.DeveloperSettingsPresenter;
import ru.shmakova.detbr.ui.fragments.DeveloperSettingsFragment;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);

    DeveloperSettingsPresenter presenter();
}
