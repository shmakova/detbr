package ru.shmakova.detbr.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.card.CardPresenter;
import ru.shmakova.detbr.data.cards.CardsRepository;

@Module
public class CardModule {
    @Provides
    @NonNull
    public CardPresenter provideCardPresenter(@NonNull CardsRepository cardsRepository) {
        return new CardPresenter(cardsRepository);
    }
}
