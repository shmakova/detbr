package ru.yandex.detbr.data.repository.models;

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
        return new AutoValue_Card.Builder()
                .cover("")
                .like(false);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder url(String url);

        public abstract Builder cover(@Nullable String cover);

        public abstract Builder like(boolean like);

        public abstract Card build();
    }
}