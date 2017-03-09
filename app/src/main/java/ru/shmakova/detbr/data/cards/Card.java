package ru.shmakova.detbr.data.cards;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.List;

import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;
import ru.shmakova.detbr.utils.UrlUtils;

@AutoValue
@FirebaseValue
public abstract class Card implements Parcelable {
    public static final String TEXT_TYPE = "text";
    public static final String PLAIN_IMAGE_TYPE = "plain_image";
    public static final String FULL_IMAGE_TYPE = "full_image";
    public static final String VIDEO_TYPE = "video";

    @Nullable
    public abstract String title();

    @Nullable
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

    @Nullable
    public abstract String color();

    public abstract boolean dark();

    @Nullable
    public abstract List<String> likes();

    public String getSiteName() {
        if (site() == null || site().isEmpty()) {
            return UrlUtils.getHost(url());
        } else {
            return site();
        }
    }

    public static Card create(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(AutoValue_Card.FirebaseValue.class).toAutoValue();
    }

    public Object toFirebaseValue() {
        return new AutoValue_Card.FirebaseValue(this);
    }

    public static Builder builder() {
        return new AutoValue_Card.Builder()
                .like(false)
                .dark(false);
    }

    public Card withLike(boolean like) {
        return new AutoValue_Card.Builder(this)
                .like(like)
                .build();
    }

    public Card withImage(String image) {
        return new AutoValue_Card.Builder(this)
                .image(image)
                .build();
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

        public abstract Builder color(@Nullable String color);

        public abstract Builder likes(@Nullable List<String> likes);

        public abstract Builder dark(boolean dark);

        public abstract Card build();
    }
}
