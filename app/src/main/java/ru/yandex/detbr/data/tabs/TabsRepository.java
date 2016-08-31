package ru.yandex.detbr.data.tabs;

import java.util.List;

import ru.yandex.detbr.data.tabs.models.Tab;
import rx.Observable;

/**
 * Created by shmakova on 29.08.16.
 */

public interface TabsRepository {
    Observable<List<Tab>> getSavedTabs();

    void addTab(Tab tab);

    void removeTab(Tab tab);

    void removeLastTab();
}
