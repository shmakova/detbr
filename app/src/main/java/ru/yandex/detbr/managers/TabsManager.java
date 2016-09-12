package ru.yandex.detbr.managers;

import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import ru.yandex.detbr.data.tabs.Tab;
import ru.yandex.detbr.data.tabs.TabsRepository;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by shmakova on 30.08.16.
 */

public class TabsManager {
    private final TabsRepository tabsRepository;
    private OnTabsChangeListener onTabsChangeListener;

    public interface OnTabsChangeListener {
        void onTabsChange();
    }

    public TabsManager(TabsRepository tabsRepository) {
        this.tabsRepository = tabsRepository;
    }

    public void setOnTabsChangeListener(OnTabsChangeListener onTabsChangeListener) {
        this.onTabsChangeListener = onTabsChangeListener;
    }

    public void updateTabs() {
        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public Observable<PutResult> addTab(Tab tab) {
        Timber.e("TAB ADDED");
        return tabsRepository.addTab(tab);
    }

    public void updateTab(Tab tab) {
        Timber.e("TAB UPDATED");
        tabsRepository.removeLastTab();
        tabsRepository.addTab(tab);

        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public void removeTab(Tab tab) {
        Timber.e("TAB REMOVED");
        tabsRepository.removeTab(tab);

        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public Observable<List<Tab>> getTabs() {
        return tabsRepository.getSavedTabs();
    }
}
