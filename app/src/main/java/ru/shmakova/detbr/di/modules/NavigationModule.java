package ru.shmakova.detbr.di.modules;

import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.managers.NavigationManager;
import ru.shmakova.detbr.ui.activities.MainActivity;

/**
 * Created by shmakova on 29.08.16.
 */

@Module
public class NavigationModule {
    private final MainActivity mainActivity;

    public NavigationModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    MainActivity provideMainActivity() {
        return mainActivity;
    }

    @Provides
    FragmentManager provideFragmentManager(MainActivity activity) {
        return activity.getSupportFragmentManager();
    }

    @Provides
    NavigationManager provideNavigationManager(FragmentManager manager, MainActivity mainActivity) {
        NavigationManager navigationManager = new NavigationManager();
        navigationManager.init(manager, mainActivity);
        return navigationManager;
    }
}
