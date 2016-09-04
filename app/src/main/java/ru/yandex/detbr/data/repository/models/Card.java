package ru.yandex.detbr.data.repository.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;


/**
 * Created by shmakova on 21.08.16.
 */

@AutoValue
@FirebaseValue
public abstract class Card implements Parcelable {
    public abstract String title();

    public abstract String url();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String image();

    @Nullable
    public abstract String site();

    @Nullable
    public abstract String favicon();

    @Exclude
    public abstract boolean like();

    @Nullable
    public abstract String category();

    @Nullable
    public abstract String type();


    public static Card create(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(AutoValue_Card.FirebaseValue.class).toAutoValue();
    }

    public Object toFirebaseValue() {
        return new AutoValue_Card.FirebaseValue(this);
    }

    public static Builder builder() {
        return new AutoValue_Card.Builder()
                .image("")
                .site("")
                .favicon("")
                .description("")
                .like(false)
                .type("plain");
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder url(String url);

        public abstract Builder description(@Nullable String description);

        public abstract Builder image(@Nullable String image);

        public abstract Builder site(@Nullable String site);

        public abstract Builder favicon(@Nullable String favicon);

        public abstract Builder like(boolean like);

        public abstract Builder category(@Nullable String category);

        public abstract Builder type(@Nullable String type);

        public abstract Card build();
    }
}