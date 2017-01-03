package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.MainModule;
import ru.shmakova.detbr.di.modules.NavigationModule;
import ru.shmakova.detbr.di.scopes.PerActivity;
import ru.shmakova.detbr.presentation.presenters.MainPresenter;
import ru.shmakova.detbr.ui.activities.MainActivity;

/**
 * Created by shmakova on 21.08.16.
 */

@PerActivity
@Subcomponent(modules = {
        MainModule.class,
        NavigationModule.class
})
public interface MainComponent {
    void inject(@NonNull MainActivity mainActivity);

    MainPresenter presenter();
}