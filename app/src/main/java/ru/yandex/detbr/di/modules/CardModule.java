package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.cards.CardsRepository;
import ru.yandex.detbr.presentation.presenters.CardPresenter;

/**
 * Created by shmakova on 04.09.16.
 */

@Module
public class CardModule {
    @Provides
    @NonNull
    public CardPresenter provideCardPresenter(@NonNull CardsRepository cardsRepository) {
        return new CardPresenter(cardsRepository);
    }
}
