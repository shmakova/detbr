package ru.yandex.detbr.managers;

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import ru.yandex.detbr.data.tabs.Tab;
import ru.yandex.detbr.data.tabs.TabsRepository;
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

    public void updateTabs() {
        if (onTabsChangeListener != null) {
            onTabsChangeListener.onTabsChange();
        }
    }

    public Observable<PutResult> addTab(Tab tab) {
        return tabsRepository.addTab(tab);
    }

    public Observable<Object> removeLastTab() {
        return tabsRepository.removeLastTab();
    }


    public Observable<DeleteResult> removeTab(Tab tab) {
        return tabsRepository.removeTab(tab);
    }

    public Observable<List<Tab>> getTabs() {
        return tabsRepository.getSavedTabs();
    }
}
