package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.di.components.MainComponent;
import ru.yandex.detbr.di.modules.DeveloperSettingsModule;
import ru.yandex.detbr.di.modules.MainModule;
import ru.yandex.detbr.ui.fragments.CardFragment;
import ru.yandex.detbr.ui.fragments.CardsPagerFragment;
import ru.yandex.detbr.ui.fragments.CategoriesFragment;
import ru.yandex.detbr.ui.fragments.CategoryCardsPagerFragmentBuilder;
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
    @BindView(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    @Inject
    SharedPreferences sharedPreferences;

    private FragmentManager supportFragmentManager;
    private String school;
    private ActionBar actionBar;
    private MainComponent mainComponent;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);

        showIntro();
        supportFragmentManager = getSupportFragmentManager();
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        bottomBar.setOnTabSelectListener(this);
        floatingSearchView.setOnMenuItemClickListener(this);
        floatingSearchView.setOnSearchListener(this);

        loadDataFromSharedPreference();

        if (school == null) {
            showSchoolsFragment();
        } else if (savedInstanceState == null) {
            showCardsFragment();
        }
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return mainComponent.presenter();
    }

    private void injectDependencies() {
        mainComponent = App.get(this).applicationComponent().plus(new MainModule());
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
        launchBrowser(card.getUrl());
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_cards:
                showCardsFragment();
                return;
            case R.id.tab_favorites:
                showFavouritesFragment();
                return;
            case R.id.tab_tabs:
                return;
            default:
        }
    }

    private void showNavigationBars() {
        bottomBar.setVisibility(View.VISIBLE);
        actionBar.hide();
        floatingSearchView.setVisibility(View.VISIBLE);
    }

    private void showCardsFragment() {
        showNavigationBars();
        showFragment(new CardsPagerFragment());
    }

    private void showFavouritesFragment() {
        showNavigationBars();
        showFragment(new FavoritesFragment());
    }

    private void showSchoolsFragment() {
        bottomBar.setVisibility(View.GONE);
        floatingSearchView.setVisibility(View.GONE);
        actionBar.show();
        showFragment(new SchoolsFragment());
    }

    private void showCategoryCardsFragment(Category category) {
        bottomBar.setVisibility(View.GONE);
        actionBar.show();
        floatingSearchView.setVisibility(View.GONE);
        Fragment fragment = new CategoryCardsPagerFragmentBuilder(category).build();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, fragment)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(null)
                .commit();
    }

    private void showFragment(Fragment fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, fragment)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    private void launchBrowser(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }

    void loadDataFromSharedPreference() {
        school = sharedPreferences.getString(DataRepository.SCHOOL_TAG, null);
    }

    @Override
    public void onSchoolClick() {
        loadDataFromSharedPreference();
        showCardsFragment();
    }

    @Override
    public void onCategoriesItemClick(Category category) {
        showCategoryCardsFragment(category);
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
        showNavigationBars();
    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_voice_rec) {
            showSpeechRecognizer();
        }
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        // no suggestions yet
    }

    @Override
    public void onSearchAction(String currentQuery) {
        launchBrowser(currentQuery);
    }

    public void updateToolbar(String title, Boolean isDisplayHomeAsUpEnabled, String color) {
        if (actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled);
            actionBar.setTitle(title);

            if (color != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
            }
        }
    }

    public void resetToolbar() {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.color_primary)));
        }
    }

    public void hideToolbar() {
        resetToolbar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
