package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import java.net.URI;
import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.yandex.detbr.browser.BrowserModel;
import ru.yandex.detbr.ui.views.BrowserView;
import ru.yandex.detbr.wot.WotResponse;
import ru.yandex.detbr.wot.WotService;
import timber.log.Timber;

/**
 * Created by shmakova on 19.08.16.
 */

public class BrowserPresenter extends Presenter<BrowserView> {
    @NonNull
    private final BrowserModel browserModel;

    @NonNull
    private final WotService wotService;

    public BrowserPresenter(@NonNull BrowserModel browserModel, @NonNull WotService wotService) {
        this.browserModel = browserModel;
        this.wotService = wotService;
    }

    public void loadUrl(String query) {
        String safeUrl = browserModel.getSafeUrl(query);
        checkUrlBeforeLoad(safeUrl);
    }

    private void checkUrlBeforeLoad(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost() + "/";
            Call<WotResponse> call = wotService.getLinkReputation(domain);

            call.enqueue(new Callback<WotResponse>() {
                @Override
                public void onResponse(Call<WotResponse> call, Response<WotResponse> response) {
                    WotResponse wotResponse = response.body();

                    final BrowserView view = view();

                    if (view != null) {
                        if (wotResponse.isSafe()) {
                            view.loadPageByUrl(url);
                        } else {
                            view.showError();
                        }
                    }
                }

                @Override
                public void onFailure(Call<WotResponse> call, Throwable t) {
                    Timber.e(t.getMessage());
                }
            });
        } catch (URISyntaxException e) {
            Timber.e(e.getMessage());
        }
    }
}
