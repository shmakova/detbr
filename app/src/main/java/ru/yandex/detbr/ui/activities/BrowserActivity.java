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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import javax.inject.Inject;

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
    @Inject
    BrowserPresenter presenter;

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.like_fab)
    FloatingActionButton fabLike;

    @Nullable
    private UrlListener listener;
    private BrowserComponent browserComponent;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        browserComponent = App.get(this).applicationComponent().plus(new BrowserModule());
        browserComponent.inject(this);

        floatingSearchView.setOnMenuItemClickListener(this);
        floatingSearchView.setOnSearchListener(this);
        floatingSearchView.setOnHomeActionClickListener(this);

        initWebView();

        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
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
        webView.setDrawingCacheBackgroundColor(Color.WHITE);
        webView.setFocusableInTouchMode(true);
        webView.setFocusable(true);
        webView.setDrawingCacheEnabled(false);
        webView.setWillNotCacheDrawing(true);
        webView.setBackgroundColor(Color.WHITE);
        webView.setScrollbarFadingEnabled(true);
        webView.setSaveEnabled(true);
        webView.setNetworkAvailable(true);
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void loadPageByUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.unsafe_page, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String query = "https://ya.ru";

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
        finish();
    }

    @Override
    public void setOnUrlListener(UrlListener listener) {
        this.listener = listener;
    }

    @Override
    public void setWebViewCallbacks(WebViewClient client) {
        if (client == null) {
            client = new WebViewClient();
        }
        webView.setWebViewClient(client);
    }

    @Override
    public void showSearchText(@Nullable String title, @NonNull String url) {
        floatingSearchView.setSearchText(url);
    }

    @Override
    public void showProgress() {
        floatingSearchView.showProgress();
    }

    @Override
    public void hideProgress() {
        floatingSearchView.hideProgress();
    }

    @Override
    public void resetLike() {
        fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_favorite_border_24dp, null));
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
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
        fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_favorite_24dp, null));
    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_voice_rec:
                displaySpeechRecognizer();
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
