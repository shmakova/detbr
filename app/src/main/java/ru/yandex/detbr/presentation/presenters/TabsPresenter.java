package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.tabs.models.Tab;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.managers.TabsManager;
import ru.yandex.detbr.presentation.views.TabsView;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shmakova on 29.08.16.
 */

public class TabsPresenter extends BaseRxPresenter<TabsView, List<Tab>>
        implements TabsManager.OnTabsChangeListener {
    private final NavigationManager navigationManager;
    private final TabsManager tabsManager;
    private final CompositeSubscription compositeSubscription;


    public TabsPresenter(@NonNull NavigationManager navigationManager,
                         @NonNull TabsManager tabsManager) {
        this.navigationManager = navigationManager;
        this.tabsManager = tabsManager;
        this.tabsManager.setOnTabsChangeListener(this);
        compositeSubscription = new CompositeSubscription();
    }

    public void loadTabs(boolean pullToRefresh) {
        Observable<List<Tab>> observable = tabsManager.getTabs();
        subscribe(observable, pullToRefresh);
    }

    public void onTabClick(Observable<Tab> positionClicks) {
        if (isViewAttached()) {
            compositeSubscription.add(
                    positionClicks.subscribe(tab -> {
                        navigationManager.openBrowser(tab.getUrl());
                        tabsManager.removeTab(tab);
                    }));
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
        navigationManager.openBrowser("http://ya.ru");
    }

    @Override
    public void onTabsChange() {
        loadTabs(true);
    }
}
