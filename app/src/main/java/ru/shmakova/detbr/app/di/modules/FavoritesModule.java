package ru.shmakova.detbr.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.favorites.FavoritesPresenter;

@Module
public class FavoritesModule {
    @Provides
    @NonNull
    public FavoritesPresenter provideFavoritesPresenter(@NonNull CardsRepository cardsRepository) {
        return new FavoritesPresenter(cardsRepository);
    }
}
