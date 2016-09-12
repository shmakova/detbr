package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import ru.yandex.detbr.data.tabs.Tab;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.managers.TabsManager;
import ru.yandex.detbr.presentation.views.TabsView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

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
                        removeTabFromDb(tab);
                    }));
        }
    }

    public void removeTab(int position, Tab tab) {
        removeTabFromDb(position, tab);
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

    private void removeTabFromDb(int position, Tab tab) {
        tabsManager
                .removeTab(tab)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteResult -> {
                            tabsManager.updateTabs();
                            if (isViewAttached()) {
                                getView().notifyItemRemoved(position);
                            }
                        },
                        throwable -> Timber.e("Error removing tab"),
                        () -> Timber.e("Completed removing tab"));
    }

    private void removeTabFromDb(Tab tab) {
        tabsManager
                .removeTab(tab)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteResult -> tabsManager.updateTabs(),
                        throwable -> Timber.e("Error removing tab"),
                        () -> Timber.e("Completed removing tab"));
    }
}
