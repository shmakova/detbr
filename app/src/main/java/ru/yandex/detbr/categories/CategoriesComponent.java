package ru.yandex.detbr.categories;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.CategoriesFragment;

/**
 * Created by shmakova on 24.08.16.
 */

@Subcomponent
public interface CategoriesComponent {
    void inject(@NonNull CategoriesFragment categoriesFragment);
}
