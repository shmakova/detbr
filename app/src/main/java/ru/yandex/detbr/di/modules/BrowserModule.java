package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.cards.CardsRepository;
import ru.yandex.detbr.data.wot_network.WotService;
import ru.yandex.detbr.managers.LikeManager;
import ru.yandex.detbr.managers.TabsManager;
import ru.yandex.detbr.presentation.presenters.BrowserPresenter;

/**
 * Created by shmakova on 20.08.16.
 */

@Module
public class BrowserModule {
    @Provides
    @NonNull
    public BrowserPresenter provideBrowserPresenter(@NonNull WotService wotService,
                                                    @NonNull TabsManager tabsManager,
                                                    @NonNull CardsRepository cardsRepository,
                                                    @NonNull LikeManager likeManager) {
        return new BrowserPresenter(wotService, tabsManager, cardsRepository, likeManager);
    }
}
