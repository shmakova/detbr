package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import butterknife.ButterKnife;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.categories.Category;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.schools.SchoolsModel;
import ru.yandex.detbr.ui.fragments.CardFragment;
import ru.yandex.detbr.ui.fragments.CardsPagerFragment;
import ru.yandex.detbr.ui.fragments.CategoriesFragment;
import ru.yandex.detbr.ui.fragments.CategoryCardsPagerFragmentBuilder;
import ru.yandex.detbr.ui.fragments.ContentFragment;
import ru.yandex.detbr.ui.fragments.FavouritesFragment;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;
import ru.yandex.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements
        SchoolsFragment.OnSchoolClickListener,
        ContentFragment.OnBrowserButtonClickListener,
        CardFragment.OnCardsItemClickListener,
        FavouritesFragment.OnCardsItemClickListener,
        OnTabSelectListener,
        CategoriesFragment.OnCategoriesItemClickListener,
        FloatingSearchView.OnMenuItemClickListener,
        FloatingSearchView.OnSearchListener {
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

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        supportFragmentManager = getSupportFragmentManager();

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        ButterKnife.bind(this);
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

    @Override
    public void onBrowserButtonCLick(String url) {
        launchBrowser(url);
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
                launchBrowser("http://ya.ru");
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
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, new CardsPagerFragment())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    private void showFavouritesFragment() {
        showNavigationBars();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, new FavouritesFragment())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    private void showSchoolsFragment() {
        bottomBar.setVisibility(View.GONE);
        floatingSearchView.setVisibility(View.GONE);
        actionBar.show();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, new SchoolsFragment())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
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

    private void launchBrowser(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }

    void loadDataFromSharedPreference() {
        school = sharedPreferences.getString(SchoolsModel.SCHOOL_TAG, null);
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
            displaySpeechRecognizer();
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
}
