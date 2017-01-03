package ru.shmakova.detbr.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.data.schools.SchoolsRepository;
import ru.shmakova.detbr.presentation.presenters.CardsPresenter;

/**
 * Created by shmakova on 21.08.16.
 */

@Module
public class CardsModule {
    @Provides
    @NonNull
    public CardsPresenter provideCardsPresenter(@NonNull CardsRepository cardsRepository,
                                                @NonNull SchoolsRepository schoolsRepository,
                                                @NonNull SharedPreferences sharedPreferences) {
        return new CardsPresenter(cardsRepository, schoolsRepository, sharedPreferences);
    }
}
