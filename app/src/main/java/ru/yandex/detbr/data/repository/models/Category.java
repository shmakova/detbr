package ru.yandex.detbr.data.repository.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by shmakova on 23.08.16.
 */

@AutoValue
public abstract class Category implements Parcelable {
    public abstract String getTitle();

    @Nullable
    public abstract String getCover();

    @Nullable
    public abstract String getBackgroundColor();

    public static Builder builder() {
        return new AutoValue_Category.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder cover(String cover);

        public abstract Builder backgroundColor(String backgroundColor);

        public abstract Category build();
    }
}