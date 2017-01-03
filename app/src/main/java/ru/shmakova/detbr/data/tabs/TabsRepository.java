package ru.shmakova.detbr.data.tabs;

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import rx.Observable;

/**
 * Created by shmakova on 29.08.16.
 */

public interface TabsRepository {
    Observable<List<Tab>> getSavedTabs();

    Observable<PutResult> addTab(Tab tab);

    Observable<DeleteResult> removeTab(Tab tab);

    Observable<Object> removeLastTab();
}
