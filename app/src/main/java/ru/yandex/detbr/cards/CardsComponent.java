package ru.yandex.detbr.cards;

import android.support.annotation.NonNull;

import dagger.Subcomponent;
import ru.yandex.detbr.ui.fragments.CardsFragment;

/**
 * Created by shmakova on 21.08.16.
 */

@Subcomponent
public interface CardsComponent {
    void inject(@NonNull CardsFragment cardsFragment);
}