package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.MainModule;
import ru.yandex.detbr.di.scopes.PerActivity;
import ru.yandex.detbr.ui.activities.MainActivity;
import ru.yandex.detbr.ui.presenters.MainPresenter;

/**
 * Created by shmakova on 21.08.16.
 */

@PerActivity
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    void inject(@NonNull MainActivity mainActivity);

    MainPresenter presenter();
}