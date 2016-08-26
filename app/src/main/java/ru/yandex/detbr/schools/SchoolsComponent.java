package ru.yandex.detbr.schools;


import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;

@Subcomponent
public interface SchoolsComponent {
    void inject(@NonNull SchoolsFragment schoolsFragment);
}
