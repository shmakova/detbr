package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.MainModule;
import ru.shmakova.detbr.app.di.modules.NavigationModule;
import ru.shmakova.detbr.app.di.scopes.PerActivity;
import ru.shmakova.detbr.main.MainActivity;
import ru.shmakova.detbr.main.MainPresenter;

@PerActivity
@Subcomponent(modules = {
        MainModule.class,
        NavigationModule.class
})
public interface MainComponent {
    void inject(@NonNull MainActivity mainActivity);

    MainPresenter presenter();
}