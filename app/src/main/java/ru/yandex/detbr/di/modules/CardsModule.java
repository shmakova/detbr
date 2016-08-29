package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.ui.presenters.CardsPresenter;

/**
 * Created by shmakova on 21.08.16.
 */

@Module
public class CardsModule {
    @Provides
    @NonNull
    public CardsPresenter provideCardsPresenter(@NonNull DataRepository dataRepository) {
        return new CardsPresenter(dataRepository);
    }
}
