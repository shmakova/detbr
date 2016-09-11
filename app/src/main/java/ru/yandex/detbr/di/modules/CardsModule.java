package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.cards.CardsRepository;
import ru.yandex.detbr.data.schools.SchoolsRepository;
import ru.yandex.detbr.presentation.presenters.CardsPresenter;

/**
 * Created by shmakova on 21.08.16.
 */

@Module
public class CardsModule {
    @Provides
    @NonNull
    public CardsPresenter provideCardsPresenter(@NonNull CardsRepository cardsRepository,
                                                @NonNull SchoolsRepository schoolsRepository) {
        return new CardsPresenter(cardsRepository, schoolsRepository);
    }
}
