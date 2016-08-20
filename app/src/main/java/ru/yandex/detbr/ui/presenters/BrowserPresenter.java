package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import ru.yandex.detbr.browser.BrowserUrlUtils;
import ru.yandex.detbr.ui.views.BrowserView;

/**
 * Created by shmakova on 19.08.16.
 */

public class BrowserPresenter extends Presenter<BrowserView> {
    @Override
    public void bindView(@NonNull BrowserView view) {
        super.bindView(view);
    }

    public void loadUrl(String query) {
        String safeUrl = BrowserUrlUtils.getSafeUrl(query);
        final BrowserView view = view();

        if (view != null) {
            view.loadPageByUrl(safeUrl);
        }
    }
}
