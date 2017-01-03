package ru.shmakova.detbr.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.di.modules.BrowserModule;
import ru.shmakova.detbr.di.scopes.PerActivity;
import ru.shmakova.detbr.presentation.presenters.BrowserPresenter;
import ru.shmakova.detbr.ui.activities.BrowserActivity;

/**
 * Created by shmakova on 20.08.16.
 */

@PerActivity
@Subcomponent(modules = BrowserModule.class)
public interface BrowserComponent {
    void inject(@NonNull BrowserActivity browserActivity);

    BrowserPresenter presenter();
}
