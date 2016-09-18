package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xwalk.core.XWalkGetBitmapCallback;
import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.di.components.BrowserComponent;
import ru.yandex.detbr.di.modules.BrowserModule;
import ru.yandex.detbr.managers.NavigationManager;
import ru.yandex.detbr.presentation.presenters.BrowserPresenter;
import ru.yandex.detbr.presentation.views.BrowserView;
import timber.log.Timber;

public class BrowserActivity extends BaseMvpActivity<BrowserView, BrowserPresenter> implements
        BrowserView {
    private static final String CHILD_SAFETY_HTML = "file:///android_asset/child_safety.html";
    private static final String LUCKY_PAGE_HTML = "file:///android_asset/lucky_page.html";

    @BindView(R.id.xwalkview)
    XWalkView xWalkView;
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
    @BindView(R.id.separator)
    View separator;

    @Nullable
    private UrlListener listener;
    @Nullable
    private BrowserComponent browserComponent;
    private SearchView searchView;
    private MenuItem searchItem;

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
            xWalkView.restoreState(savedInstanceState);
        }
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        xWalkView.saveState(outState);
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
        xWalkView.setResourceClient(new MyResourceClient(xWalkView));
        xWalkView.setUIClient(new MyUIClient(xWalkView));
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
        xWalkView.stopLoading();
        xWalkView.load(url, null);
    }

    @Override
    public void showError() {
        xWalkView.load(CHILD_SAFETY_HTML, null);
    }

    @Override
    public void showLuckyPage() {
        xWalkView.load(LUCKY_PAGE_HTML, null);
    }

    @Override
    public void showLike() {
        fabLike.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLike() {
        fabLike.setVisibility(View.GONE);
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
        finish();
    }

    @Override
    public void updateProgress(int newProgress) {
        progressBar.setProgress(newProgress);
    }

    @Override
    public void openTabs() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NavigationManager.TAB_KEY, NavigationManager.TABS_TAB_POSITION);
        startActivity(intent);
        finish();
    }

    @Override
    public void setOnUrlListener(UrlListener listener) {
        this.listener = listener;
    }

    @Override
    public void showSearchText(@Nullable String title, @Nullable String host) {
        if (title == null || title.isEmpty() || host == null || host.isEmpty()) {
            separator.setVisibility(View.GONE);
        } else {
            separator.setVisibility(View.VISIBLE);
        }

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
    public void showLike(boolean like) {
        if (like) {
            fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_like_fill, null));
        } else {
            fabLike.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_like_black_border, null));
        }
    }

    @Override
    public void onBackPressed() {
        if (xWalkView.getNavigationHistory().canGoBack()) {
            xWalkView.getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
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
                presenter.onTabsClicked();
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, xWalkView.getUrl());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
    }

    @OnClick(R.id.like_fab)
    public void onFabLikeClick() {
        presenter.onLikeClick(xWalkView.getTitle(), xWalkView.getUrl());
    }

    @OnClick(R.id.toolbar)
    public void onToolbarClick() {
        MenuItemCompat.expandActionView(searchItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (xWalkView != null) {
            xWalkView.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);

        if (xWalkView != null) {
            xWalkView.onNewIntent(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkView != null) {
            xWalkView.pauseTimers();
            xWalkView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkView != null) {
            xWalkView.resumeTimers();
            xWalkView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkView != null) {
            xWalkView.onDestroy();
        }
    }

    class MyResourceClient extends XWalkResourceClient {

        MyResourceClient(XWalkView view) {
            super(view);
        }

        @Override
        public void onLoadStarted(XWalkView view, String url) {
            Timber.w("onLoadStarted: " + url);
        }

        @Override
        public void onLoadFinished(XWalkView view, String url) {
            Timber.w("onLoadFinished: " + url);
        }

        @Override
        public void onProgressChanged(XWalkView view, int newProgress) {
            Timber.w("onProgressChanged: " + newProgress);
        }

        @Override
        public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
            Timber.w("shouldOverrideUrlLoading: " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view,
                                                                   XWalkWebResourceRequest request) {
            Timber.w("shouldInterceptLoadRequest: url: " + request.getUrl()
                    + ", method: " + request.getMethod());
            return super.shouldInterceptLoadRequest(view, request);
        }
    }

    /**
     * Example of XWalkUIClient implementation
     */
    class MyUIClient extends XWalkUIClient {

        MyUIClient(XWalkView view) {
            super(view);
        }

        @Override
        public void onPageLoadStarted(XWalkView view, java.lang.String url) {
            Timber.w("onPageLoadStarted: " + url);
        }

        @Override
        public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
            Timber.w("onPageLoadStopped: " + url + ", status: " + status);

            if (status == LoadStatus.FINISHED) {
                view.captureBitmapAsync(new XWalkGetBitmapCallback() {
                    @Override
                    public void onFinishGetBitmap(Bitmap bitmap, int i) {
                        Timber.w("onFinishGetBitmap: " + bitmap);
                    }
                });
            }
        }
    }
}
