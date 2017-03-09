package ru.shmakova.detbr.app.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.cards.CardsPresenter;
import ru.shmakova.detbr.data.cards.CardsRepository;

@Module
public class CardsModule {
    @Provides
    @NonNull
    public CardsPresenter provideCardsPresenter(@NonNull CardsRepository cardsRepository,
                                                @NonNull SharedPreferences sharedPreferences) {
        return new CardsPresenter(cardsRepository, sharedPreferences);
    }
}
