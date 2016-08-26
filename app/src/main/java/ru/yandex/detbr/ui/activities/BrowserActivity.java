package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.ui.presenters.BrowserPresenter;
import ru.yandex.detbr.ui.views.BrowserView;

public class BrowserActivity extends BaseActivity implements
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

    private String currentQuery;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.get(this).applicationComponent().browserComponent().inject(this);
        presenter.bindView(this);

        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
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
                updateToolbar(url);
                hideProgressBar();
                view.postInvalidate();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                updateToolbar(url);
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
        String query = currentQuery;

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();

            if (uri != null) {
                query = uri.toString();
            }
        }

        presenter.loadUrl(query);
    }

    @Override
    public void updateToolbar(@NonNull String url) {
        floatingSearchView.setSearchText(url);
    }

    @Override
    public void showProgressBar() {
        floatingSearchView.showProgress();
    }

    @Override
    public void hideProgressBar() {
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

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_voice_rec:
                displaySpeechRecognizer();
                break;
            case R.id.home_page:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        presenter.loadUrl(currentQuery);
    }

    @Override
    public void onHomeClicked() {
        onBackPressed();
    }
}
