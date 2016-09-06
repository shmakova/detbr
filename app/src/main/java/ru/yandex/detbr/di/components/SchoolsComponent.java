package ru.yandex.detbr.di.components;


import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.SchoolsModule;
import ru.yandex.detbr.di.scopes.PerFragment;
import ru.yandex.detbr.presentation.presenters.SchoolsPresenter;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;

@PerFragment
@Subcomponent(modules = SchoolsModule.class)
public interface SchoolsComponent {
    void inject(@NonNull SchoolsFragment schoolsFragment);

    SchoolsPresenter presenter();
}
