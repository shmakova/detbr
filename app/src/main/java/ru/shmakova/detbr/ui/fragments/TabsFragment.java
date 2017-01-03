package ru.shmakova.detbr.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.shmakova.detbr.App;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.di.components.TabsComponent;
import ru.shmakova.detbr.di.modules.NavigationModule;
import ru.shmakova.detbr.di.modules.TabsModule;
import ru.shmakova.detbr.presentation.presenters.TabsPresenter;
import ru.shmakova.detbr.presentation.views.TabsView;
import ru.shmakova.detbr.ui.activities.MainActivity;
import ru.shmakova.detbr.ui.adapters.TabsAdapter;
import ru.shmakova.detbr.utils.ErrorMessageDeterminer;

/**
 * Created by shmakova on 29.08.16.
 */

public class TabsFragment extends BaseLceFragment<FrameLayout, List<Tab>, TabsView, TabsPresenter> implements TabsView {
    @Inject
    ErrorMessageDeterminer errorMessageDeterminer;

    @BindView(R.id.tabs_list)
    RecyclerView recyclerView;

    private TabsComponent tabsComponent;
    private TabsAdapter adapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    private void injectDependencies() {
        tabsComponent = App.get(getContext())
                .applicationComponent()
                .plus(new TabsModule(), new NavigationModule((MainActivity) getActivity()));
        tabsComponent.inject(this);
    }

    @NonNull
    @Override
    public TabsPresenter createPresenter() {
        return tabsComponent.presenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TabsAdapter(tab -> presenter.removeTab(tab));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return errorMessageDeterminer.getErrorMessage(e, pullToRefresh);
    }

    @NonNull
    @Override
    public LceViewState<List<Tab>, TabsView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Tab> getData() {
        return (adapter == null) ? null : adapter.getTabs();
    }

    @Override
    public void setData(List<Tab> data) {
        if (adapter != null) {
            adapter.setTabs(data);
            adapter.notifyDataSetChanged();
            presenter.onTabClick(adapter.getPositionClicks());
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadTabs(pullToRefresh);
    }

    @OnClick(R.id.add_tab_fab)
    public void onAddTabClick() {
        presenter.onAddTabClick();
    }
}