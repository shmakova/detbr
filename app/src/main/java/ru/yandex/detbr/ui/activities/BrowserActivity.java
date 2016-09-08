package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.di.components.BrowserComponent;
import ru.yandex.detbr.di.modules.BrowserModule;
import ru.yandex.detbr.presentation.presenters.BrowserPresenter;
import ru.yandex.detbr.presentation.views.BrowserView;

public class BrowserActivity extends BaseMvpActivity<BrowserView, BrowserPresenter> implements
        BrowserView {
    private static final String CHILD_SAFETY_HTML = "file:///android_asset/child_safety.html";

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.like_fab)
    FloatingActionButton fabLike;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.browser_host)
    TextView browserHost;
    @BindView(R.id.browser_title)
    TextView browserTitle;

    @Nullable
    private UrlListener listener;
    @Nullable
    private BrowserComponent browserComponent;
    private SearchView searchView;
    private MenuItem searchItem;
    private ActionBar actionBar;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        initActionBar();
        initWebView();

        if (savedInstanceState == null) {
            handleIntent(getIntent());
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void injectDependencies() {
        browserComponent = App.get(this).applicationComponent().plus(new BrowserModule());
        browserComponent.inject(this);
    }

    @NonNull
    @Override
    public BrowserPresenter createPresenter() {
        return browserComponent.presenter();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setDrawingCacheBackgroundColor(Color.WHITE);
        webView.setFocusableInTouchMode(true);
        webView.setFocusable(true);
        webView.setDrawingCacheEnabled(false);
        webView.setWillNotCacheDrawing(true);
        webView.setBackgroundColor(Color.WHITE);
        webView.setScrollbarFadingEnabled(true);
        webView.setSaveEnabled(true);
        webView.setNetworkAvailable(true);
        webView.setWebViewClient(presenter.provideWebViewClient());
        webView.setWebChromeClient(presenter.provideWebChromeClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchItem = menu.findItem(R.id.search);
        searchItem.setVisible(false);

        return true;
    }

    @Override
    public void loadPageByUrl(String url) {
        webView.stopLoading();
        webView.loadUrl(url);
    }

    @Override
    public void showError() {
        webView.loadUrl(CHILD_SAFETY_HTML);
    }

    @Override
    public void showLike() {
        fabLike.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLike() {
        fabLike.setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String query = "";

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();

            if (uri != null) {
                query = uri.toString();
            }
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
        }

        if (listener != null) {
            listener.onUrl(query);
        }
    }

    @Override
    public void close() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateProgress(int newProgress) {
        progressBar.setProgress(newProgress);
    }

    @Override
    public void setOnUrlListener(UrlListener listener) {
        this.listener = listener;
    }

    @Override
    public void showSearchText(@Nullable String title, @NonNull String host) {
        browserTitle.setText(title);
        browserHost.setText(host);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void setLike(boolean like) {
        if (like) {
            fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_favorite_24dp, null));
        } else {
            fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_favorite_border_24dp, null));
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            presenter.onHomeClicked();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.tabs_page:
                presenter.onHomeClicked();
                return true;
            case R.id.share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
    }

    @OnClick(R.id.like_fab)
    public void onFabLikeClick() {
        presenter.onLikeClick(webView.getTitle(), webView.getUrl());
    }

    @OnClick(R.id.toolbar)
    public void onToolbarClick() {
        MenuItemCompat.expandActionView(searchItem);
    }
}
