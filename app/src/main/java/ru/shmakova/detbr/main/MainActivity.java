package ru.shmakova.detbr.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import icepick.Icepick;
import ru.shmakova.detbr.BuildConfig;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.app.App;
import ru.shmakova.detbr.app.NavigationManager;
import ru.shmakova.detbr.app.di.components.MainComponent;
import ru.shmakova.detbr.app.di.modules.DeveloperSettingsModule;
import ru.shmakova.detbr.app.di.modules.MainModule;
import ru.shmakova.detbr.app.di.modules.NavigationModule;
import ru.shmakova.detbr.base.BaseMvpActivity;
import ru.shmakova.detbr.card.IntroFragment;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.ui.listeners.OnCardsItemClickListener;
import ru.shmakova.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements
        OnCardsItemClickListener,
        OnTabSelectListener,
        IntroFragment.OnStartClickListener,
        FloatingSearchView.OnSearchListener,
        MainView {

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    @BindView(R.id.content_wrapper)
    View contentWrapper;
    @BindView(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private MainComponent mainComponent;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        Intent intent = getIntent();
        int tabId = intent.getIntExtra(NavigationManager.TAB_KEY, -1);

        if (BuildConfig.DEBUG) {
            setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        } else {
            setContentView(R.layout.activity_main);
        }

        bottomBar.setOnTabSelectListener(this);
        floatingSearchView.setOnSearchListener(this);

        if (savedInstanceState == null) {
            presenter.onFirstLoad(tabId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return mainComponent.presenter();
    }

    private void injectDependencies() {
        mainComponent = App.get(this).applicationComponent().plus(new MainModule(), new NavigationModule(this));
        mainComponent.inject(this);
    }

    @Override
    public void onCardsItemClick(Card card) {
        presenter.onCardsItemClick(card);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        presenter.onTabSelected(tabId);
    }

    @Override
    public void showNavigationBars() {
        bottomBar.setVisibility(View.VISIBLE);
        floatingSearchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNavigationBars() {
        bottomBar.setVisibility(View.GONE);
        floatingSearchView.setVisibility(View.GONE);
    }

    @Override
    public void hideSearchView() {
        floatingSearchView.setVisibility(View.GONE);
    }

    @Override
    public void showSearchView() {
        floatingSearchView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPressed();
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        // no suggestions yet
    }

    @Override
    public void onSearchAction(String currentQuery) {
        presenter.onSearchAction(currentQuery);
    }


    @Override
    public void changeBackgroundColor(@ColorRes int color) {
        contentWrapper.setBackgroundColor(ContextCompat.getColor(this, color));
    }

    @Override
    public void selectTabAtPosition(int position) {
        bottomBar.selectTabAtPosition(position);
    }

    @Override
    public void onStartClick() {
        presenter.onStartClick();
    }

}
