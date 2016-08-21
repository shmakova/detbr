package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import ru.yandex.detbr.browser.BrowserModel;
import ru.yandex.detbr.ui.views.BrowserView;

/**
 * Created by shmakova on 19.08.16.
 */

public class BrowserPresenter extends Presenter<BrowserView> {
    @NonNull
    private final BrowserModel browserModel;

    public BrowserPresenter(@NonNull BrowserModel browserModel) {
        this.browserModel = browserModel;
    }

    public void loadUrl(String query) {
        String safeUrl = browserModel.getSafeUrl(query);
        final BrowserView view = view();

        if (view != null) {
            view.loadPageByUrl(safeUrl);
        }
    }
}
