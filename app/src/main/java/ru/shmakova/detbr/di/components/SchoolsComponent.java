package ru.shmakova.detbr.di.components;


import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.SchoolsModule;
import ru.shmakova.detbr.di.scopes.PerFragment;
import ru.shmakova.detbr.presentation.presenters.SchoolsPresenter;
import ru.shmakova.detbr.ui.fragments.SchoolsFragment;

@PerFragment
@Subcomponent(modules = SchoolsModule.class)
public interface SchoolsComponent {
    void inject(@NonNull SchoolsFragment schoolsFragment);

    SchoolsPresenter presenter();
}
