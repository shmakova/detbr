package ru.yandex.detbr.data.tabs.models;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.net.URISyntaxException;

import timber.log.Timber;

/**
 * Created by shmakova on 29.08.16.
 */

@AutoValue
public abstract class Tab {
    @Nullable
    public abstract String getTitle();

    @Nullable
    public abstract String getUrl();

    @Nullable
    public abstract Bitmap getPreview();

    public String getHost() {
        String host = "";

        try {
            URI uri = new URI(getUrl());
            host = uri.getHost();
        } catch (URISyntaxException e) {
            Timber.e(e, "Error while parsing url");
        }

        return host;
    }

    public static Builder builder() {
        return new AutoValue_Tab.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder url(String url);

        public abstract Builder preview(Bitmap preview);

        public abstract Tab build();
    }
}
