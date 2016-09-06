package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.MainModule;
import ru.yandex.detbr.di.modules.NavigationModule;
import ru.yandex.detbr.di.scopes.PerActivity;
import ru.yandex.detbr.presentation.presenters.MainPresenter;
import ru.yandex.detbr.ui.activities.MainActivity;

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