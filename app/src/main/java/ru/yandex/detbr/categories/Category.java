package ru.yandex.detbr.categories;

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
    abstract static class Builder {
        abstract Builder title(String title);

        abstract Builder cover(String cover);

        abstract Builder backgroundColor(String backgroundColor);

        abstract Category build();
    }
}