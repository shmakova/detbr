package ru.shmakova.detbr.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.browser.BrowserPresenter;
import ru.shmakova.detbr.data.cards.CardsRepository;
import ru.shmakova.detbr.data.stopwords.StopWordsRepository;
import ru.shmakova.detbr.tabs.TabsManager;

@Module
public class BrowserModule {
    @Provides
    @NonNull
    public BrowserPresenter provideBrowserPresenter(@NonNull TabsManager tabsManager,
                                                    @NonNull CardsRepository cardsRepository,
                                                    @NonNull StopWordsRepository stopWordsRepository) {
        return new BrowserPresenter(tabsManager, cardsRepository, stopWordsRepository);
    }
}
