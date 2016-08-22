package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.ui.fragments.CardsFragment;
import ru.yandex.detbr.ui.fragments.ContentFragment;
import ru.yandex.detbr.ui.fragments.FavouritesFragment;
import ru.yandex.detbr.ui.other.ViewModifier;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements
        ContentFragment.OnBrowserButtonClickListener,
        CardsFragment.OnCardsItemClickListener,
        FavouritesFragment.OnCardsItemClickListener,
        OnTabSelectListener {
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private FragmentManager supportFragmentManager;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        supportFragmentManager = getSupportFragmentManager();

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        ButterKnife.bind(this);
        bottomBar.setOnTabSelectListener(this);

        if (savedInstanceState == null) {
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
        Timber.e(String.valueOf(tabId));

        switch (tabId) {
            case R.id.tab_cards:
                showCardsFragment();
                return;
            case R.id.tab_favorites:
                showFavouritesFragment();
                return;
            case R.id.tab_tabs:
                launchBrowser("http://ya.ru");
        }
    }

    private void showCardsFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, new CardsFragment())
                .commit();
    }

    private void showFavouritesFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, new FavouritesFragment())
                .commit();
    }

    private void launchBrowser(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
