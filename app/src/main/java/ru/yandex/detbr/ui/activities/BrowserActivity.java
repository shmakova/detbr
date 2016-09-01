package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.di.components.BrowserComponent;
import ru.yandex.detbr.di.modules.BrowserModule;
import ru.yandex.detbr.ui.presenters.BrowserPresenter;
import ru.yandex.detbr.ui.views.BrowserView;

public class BrowserActivity extends BaseMvpActivity<BrowserView, BrowserPresenter> implements
        BrowserView,
        FloatingSearchView.OnMenuItemClickListener,
        FloatingSearchView.OnSearchListener,
        FloatingSearchView.OnHomeActionClickListener {
    private static final String CHILD_SAFETY_HTML = "file:///android_asset/child_safety.html";

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.like_fab)
    FloatingActionButton fabLike;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Nullable
    private UrlListener listener;
    @Nullable
    private BrowserComponent browserComponent;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        floatingSearchView.setOnMenuItemClickListener(this);
        floatingSearchView.setOnSearchListener(this);
        floatingSearchView.setOnHomeActionClickListener(this);

        initWebView();

        if (savedInstanceState == null) {
            handleIntent(getIntent());
        } else {
            webView.restoreState(savedInstanceState);
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
    public void loadPageByUrl(String url) {
        this.webView.loadUrl(url);
    }

    @Override
    public void showError() {
        webView.loadUrl(CHILD_SAFETY_HTML);
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
    public void showSearchText(@Nullable String title, @NonNull String url) {
        floatingSearchView.setSearchText(url);
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
            case R.id.home_page:
                presenter.onHomeClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.like_fab)
    public void onFabLikeClick() {
        presenter.onLikeClick(webView.getTitle(), webView.getUrl());
    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_voice_rec:
                showSpeechRecognizer();
                break;
            case R.id.home_page:
                presenter.onHomeClicked();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        // no suggestions yet
    }

    @Override
    public void onSearchAction(String currentQuery) {
        if (listener != null) {
            listener.onUrl(currentQuery);
        }
    }

    @Override
    public void onHomeClicked() {
        onBackPressed();
    }
}
