package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.tabs.TabsRepository;
import ru.yandex.detbr.data.tabs.models.Tab;
import ru.yandex.detbr.ui.managers.NavigationManager;
import ru.yandex.detbr.ui.views.TabsView;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shmakova on 29.08.16.
 */

public class TabsPresenter extends BaseRxPresenter<TabsView, List<Tab>> {
    @NonNull
    private final TabsRepository tabsRepository;
    private final NavigationManager navigationManager;
    private final CompositeSubscription compositeSubscription;


    public TabsPresenter(@NonNull TabsRepository tabsRepository, @NonNull NavigationManager navigationManager) {
        this.tabsRepository = tabsRepository;
        this.navigationManager = navigationManager;
        compositeSubscription = new CompositeSubscription();
    }

    public void loadTabs(boolean pullToRefresh) {
        Observable<List<Tab>> observable = tabsRepository.getTabs();
        subscribe(observable, pullToRefresh);
    }

    public void onTabClick(Observable<Tab> positionClicks) {
        if (isViewAttached()) {
            compositeSubscription.add(
                    positionClicks.subscribe(tab -> navigationManager.openBrowser(tab.getUrl())));
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }

    public void onAddTabClick() {
        navigationManager.openBrowser("");
    }
}
