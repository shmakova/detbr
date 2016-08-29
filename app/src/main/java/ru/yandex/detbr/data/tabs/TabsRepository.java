package ru.yandex.detbr.data.tabs;

import java.util.List;

import ru.yandex.detbr.data.tabs.models.Tab;
import rx.Observable;

/**
 * Created by shmakova on 29.08.16.
 */

public interface TabsRepository {
    Observable<List<Tab>> getTabs();

    void addTab(Tab tab);
}
