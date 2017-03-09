package ru.shmakova.detbr.app.di.components;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.shmakova.detbr.app.di.modules.BrowserModule;
import ru.shmakova.detbr.app.di.scopes.PerActivity;
import ru.shmakova.detbr.browser.BrowserActivity;
import ru.shmakova.detbr.browser.BrowserPresenter;

@PerActivity
@Subcomponent(modules = BrowserModule.class)
public interface BrowserComponent {
    void inject(@NonNull BrowserActivity browserActivity);

    BrowserPresenter presenter();
}
