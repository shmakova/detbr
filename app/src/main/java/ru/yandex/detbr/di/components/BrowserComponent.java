package ru.yandex.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.di.modules.BrowserModule;
import ru.yandex.detbr.di.scopes.PerActivity;
import ru.yandex.detbr.presentation.presenters.BrowserPresenter;
import ru.yandex.detbr.ui.activities.BrowserActivity;

/**
 * Created by shmakova on 20.08.16.
 */

@PerActivity
@Subcomponent(modules = BrowserModule.class)
public interface BrowserComponent {
    void inject(@NonNull BrowserActivity browserActivity);

    BrowserPresenter presenter();
}
