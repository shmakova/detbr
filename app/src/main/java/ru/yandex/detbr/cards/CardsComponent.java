package ru.yandex.detbr.cards;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.BaseCardsPagerFragment;
import ru.yandex.detbr.ui.fragments.FavouritesFragment;

/**
 * Created by shmakova on 21.08.16.
 */

@Subcomponent
public interface CardsComponent {
    void inject(@NonNull BaseCardsPagerFragment baseCardsPagerFragment);

    void inject(@NonNull FavouritesFragment favouritesFragment);
}