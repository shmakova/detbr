package ru.yandex.detbr.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.di.scopes.PerActivity;
import ru.yandex.detbr.ui.activities.MainActivity;
import ru.yandex.detbr.ui.managers.NavigationManager;
import ru.yandex.detbr.ui.presenters.MainPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class MainModule {
    private final MainActivity mainActivity;

    public MainModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @NonNull
    MainPresenter provideMainPresenter(@NonNull SharedPreferences sharedPreferences,
                                       @NonNull NavigationManager navigationManager) {
        return new MainPresenter(sharedPreferences, navigationManager);
    }

    @Provides
    @PerActivity
    MainActivity provideMainActivity() {
        return mainActivity;
    }

    @Provides
    @PerActivity
    FragmentManager provideFragmentManager(MainActivity activity) {
        return activity.getSupportFragmentManager();
    }

    @Provides
    @PerActivity
    NavigationManager provideNavigationManager(FragmentManager manager, MainActivity mainActivity) {
        NavigationManager navigationManager = new NavigationManager();
        navigationManager.init(manager, mainActivity);
        return navigationManager;
    }
}
