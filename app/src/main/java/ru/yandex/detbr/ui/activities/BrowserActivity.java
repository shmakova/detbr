package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.detbr.R;
import ru.yandex.detbr.browser.DetbrWebViewClient;
import ru.yandex.detbr.ui.other.UIController;

public class BrowserActivity extends AppCompatActivity implements UIController {
    @BindView(R.id.webview)
    WebView webView;

    private ActionBar actionBar;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        initWebView();
        handleIntent(getIntent());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new DetbrWebViewClient(this));
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void loadPageByUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_LONG).show();
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();

            if (uri != null) {
                loadPageByUrl(uri.toString());
            }
        }
    }

    @Override
    public void updateUrl(@Nullable String title, @NonNull String url) {
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setSubtitle(url);
        }
    }
}
