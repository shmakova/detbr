package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.BuildConfig;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.categories.Category;
import ru.yandex.detbr.di.components.MainComponent;
import ru.yandex.detbr.di.modules.DeveloperSettingsModule;
import ru.yandex.detbr.di.modules.MainModule;
import ru.yandex.detbr.di.modules.NavigationModule;
import ru.yandex.detbr.presentation.presenters.MainPresenter;
import ru.yandex.detbr.presentation.views.MainView;
import ru.yandex.detbr.ui.fragments.CategoriesFragment;
import ru.yandex.detbr.ui.listeners.OnCardsItemClickListener;
import ru.yandex.detbr.ui.listeners.OnLikeClickListener;
import ru.yandex.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements
        OnCardsItemClickListener,
        OnLikeClickListener,
        OnTabSelectListener,
        CategoriesFragment.OnCategoriesItemClickListener,
        FloatingSearchView.OnMenuItemClickListener,
        FloatingSearchView.OnSearchListener,
        MainView {
    private static final int SPEECH_REQUEST_CODE = 1;

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.floating_search_view)
    protected FloatingSearchView floatingSearchView;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private ActionBar actionBar;
    private MainComponent mainComponent;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        } else {
            setContentView(R.layout.activity_main);
        }

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        bottomBar.setOnTabSelectListener(this);
        floatingSearchView.setOnMenuItemClickListener(this);
        floatingSearchView.setOnSearchListener(this);

        if (savedInstanceState == null) {
            presenter.onFirstLoad();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            floatingSearchView.setSearchText(spokenText);
        }

        super.onActivityResult(requestCode, resultCode, data);
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
    public void onLikeClick(Card card) {
        presenter.onLikeClick(card);
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

    public void showSpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }
}
