package ru.yandex.detbr.cards;

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

    public static Builder builder() {
        return new AutoValue_Category.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder title(String title);

        abstract Builder cover(String cover);

        abstract Category build();
    }
}