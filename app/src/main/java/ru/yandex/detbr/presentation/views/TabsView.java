package ru.yandex.detbr.presentation.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.yandex.detbr.data.tabs.Tab;

/**
 * Created by shmakova on 29.08.16.
 */

public interface TabsView extends MvpLceView<List<Tab>> {
    void notifyItemRemoved(int position);
}
