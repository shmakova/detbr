package ru.yandex.detbr.school;


import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.SchoolFragment;

@Subcomponent
public interface SchoolsDataComponent {
    void inject(@NonNull SchoolFragment schoolFragment);
}
