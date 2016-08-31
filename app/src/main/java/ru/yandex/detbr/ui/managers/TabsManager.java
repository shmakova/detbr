package ru.yandex.detbr.ui.managers;

import java.util.List;

import ru.yandex.detbr.data.tabs.TabsRepository;
import ru.yandex.detbr.data.tabs.models.Tab;
import rx.Observable;

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

    public void addTab(Tab tab) {
        tabsRepository.addTab(tab);

        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public void updateTab(Tab tab) {
        tabsRepository.removeLastTab();
        tabsRepository.addTab(tab);

        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public void removeTab(Tab tab) {
        tabsRepository.removeTab(tab);

        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public Observable<List<Tab>> getTabs() {
        return tabsRepository.getSavedTabs();
    }
}
