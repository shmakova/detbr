package ru.yandex.detbr.cards;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by shmakova on 21.08.16.
 */

@AutoValue
public abstract class Card implements Parcelable {
    public abstract String getTitle();

    public abstract String getUrl();

    @Nullable
    public abstract String getCover();

    public abstract boolean getLike();

    public static Builder builder() {
        return new AutoValue_Card.Builder().like(false);
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder title(String title);

        abstract Builder url(String url);

        abstract Builder cover(String cover);

        abstract Builder like(boolean like);

        abstract Card build();
    }
}