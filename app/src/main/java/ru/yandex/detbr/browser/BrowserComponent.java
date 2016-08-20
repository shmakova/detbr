package ru.yandex.detbr.browser;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.activities.BrowserActivity;

/**
 * Created by shmakova on 20.08.16.
 */

@Subcomponent
public interface BrowserComponent {
    void inject(@NonNull BrowserActivity browserActivity);
}
