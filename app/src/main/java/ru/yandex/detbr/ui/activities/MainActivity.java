package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.di.components.MainComponent;
import ru.yandex.detbr.di.modules.DeveloperSettingsModule;
import ru.yandex.detbr.di.modules.MainModule;
import ru.yandex.detbr.di.modules.NavigationModule;
import ru.yandex.detbr.ui.fragments.CardFragment;
import ru.yandex.detbr.ui.fragments.CategoriesFragment;
import ru.yandex.detbr.ui.fragments.FavoritesFragment;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;
import ru.yandex.detbr.ui.other.ViewModifier;
import ru.yandex.detbr.ui.presenters.MainPresenter;
import ru.yandex.detbr.ui.views.MainView;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements
        SchoolsFragment.OnSchoolClickListener,
        CardFragment.OnCardsItemClickListener,
        FavoritesFragment.OnCardsItemClickListener,
        OnTabSelectListener,
        CategoriesFragment.OnCategoriesItemClickListener,
        FloatingSearchView.OnMenuItemClickListener,
        FloatingSearchView.OnSearchListener,
        MainView {
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    @Inject
    SharedPreferences sharedPreferences;

    private ActionBar actionBar;
    private MainComponent mainComponent;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);

        showIntro();
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        bottomBar.setOnTabSelectListener(this);
        floatingSearchView.setOnMenuItemClickListener(this);
        floatingSearchView.setOnSearchListener(this);

        if (savedInstanceState == null) {
            presenter.onFirstLoad();
        }
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

    private void showIntro() {
        Thread thread = new Thread(() -> {
            boolean isFirstStart = sharedPreferences.getBoolean("firstStart", true);

            if (isFirstStart) {
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);

                sharedPreferences
                        .edit()
                        .putBoolean("firstStart", false)
                        .apply();
            }
        });

        thread.start();
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
    public void showToolbar() {
        actionBar.show();
    }

    @Override
    public void hideToolbar() {
        resetToolbar();
        actionBar.hide();
    }

    @Override
    public void onSchoolClick() {
        presenter.onSchoolClick();
    }

    @Override
    public void onCategoriesItemClick(Category category) {
        presenter.onCategoriesItemClick(category);
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
    public void onActionMenuItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.onActionMenuItemSelected(id);
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
    public void updateToolbar(String title, Boolean isDisplayHomeAsUpEnabled, String color) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled);
            actionBar.setTitle(title);

            if (color != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
            }
            actionBar.show();
        }
    }

    @Override
    public void resetToolbar() {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.color_primary)));
        }
    }
}
