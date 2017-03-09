package ru.shmakova.detbr.tabs;

import android.support.annotation.NonNull;

import java.util.List;

import ru.shmakova.detbr.app.NavigationManager;
import ru.shmakova.detbr.base.BaseRxPresenter;
import ru.shmakova.detbr.data.tabs.Tab;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

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
                        removeTabFromDb(tab);
                    }));
        }
    }

    public void removeTab(Tab tab) {
        removeTabFromDb(tab);
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

    private void removeTabFromDb(Tab tab) {
        tabsManager
                .removeTab(tab)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteResult -> tabsManager.updateTabs(),
                        throwable -> Timber.e("Error removing tab"),
                        () -> Timber.d("Completed removing tab"));
    }
}
