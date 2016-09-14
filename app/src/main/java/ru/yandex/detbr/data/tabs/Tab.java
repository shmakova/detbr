package ru.yandex.detbr.data.tabs;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;


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
