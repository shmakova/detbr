package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.ui.presenters.BrowserPresenter;
import ru.yandex.detbr.ui.views.BrowserView;

public class BrowserActivity extends AppCompatActivity implements BrowserView {
    @Inject
    BrowserPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.like_fab)
    FloatingActionButton fabLike;

    private SearchView searchView;
    private String currentQuery;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.get(this).applicationComponent().browserComponent().inject(this);
        presenter.bindView(this);

        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

        initActionBar();
        initWebView();

        handleIntent(getIntent());
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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(@NonNull WebView view, String url) {
                updateToolbar(view.getTitle(), url);
                hideProgressBar();
                view.postInvalidate();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgressBar();
                resetLike();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                presenter.loadUrl(url);
                currentQuery = url;
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                updateProgressBar(newProgress);
            }
        });
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void loadPageByUrl(String url) {
        webView.loadUrl(url);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String query = currentQuery;

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();

            if (uri != null && (currentQuery == null || currentQuery.isEmpty())) {
                query = uri.toString();
            }
        }

        presenter.loadUrl(query);
    }

    @Override
    public void updateToolbar(@Nullable String title, @NonNull String url) {
        toolbar.setTitle(title);
        toolbar.setSubtitle(url);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
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
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.like_fab)
    public void onFabLikeClick() {
        fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_favorite_24dp, null));
    }

    @Override
    protected void onDestroy() {
        presenter.unbindView(this);
        super.onDestroy();
    }
}
