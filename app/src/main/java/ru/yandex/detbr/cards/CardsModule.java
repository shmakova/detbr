package ru.yandex.detbr.cards;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.ui.presenters.CardsPresenter;

/**
 * Created by shmakova on 21.08.16.
 */

@Module
public class CardsModule {
    @Provides
    @NonNull
    public CardsPresenter provideCardsPresenter(@NonNull CardsModel cardsModel) {
        return new CardsPresenter(cardsModel);
    }

    @Provides
    @NonNull
    public CardsModel provideCardsModel(@NonNull CardsModelImpl cardsModelImpl) {
        return cardsModelImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public CardsModelImpl provideCardsModelImpl(@NonNull StorIOSQLite storIOSQLite) {
        return new CardsModelImpl(storIOSQLite);
    }
}
