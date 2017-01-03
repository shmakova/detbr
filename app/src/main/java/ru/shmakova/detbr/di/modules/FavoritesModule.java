package ru.shmakova.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.presentation.presenters.FavoritesPresenter;

/**
 * Created by shmakova on 28.08.16.
 */

@Module
public class FavoritesModule {
    @Provides
    @NonNull
    public FavoritesPresenter provideFavoritesPresenter(@NonNull CardsRepository cardsRepository) {
        return new FavoritesPresenter(cardsRepository);
    }
}