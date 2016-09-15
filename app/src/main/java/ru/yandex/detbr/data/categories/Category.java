package ru.yandex.detbr.data.categories;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;

import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;
import ru.yandex.detbr.R;

/**
 * Created by shmakova on 23.08.16.
 */

@AutoValue
@FirebaseValue
public abstract class Category implements Parcelable {
    public abstract String title();

    @Nullable
    public abstract String alias();

    @Nullable
    public abstract String color();

    public static Category create(String name, String alias, String color) {
        return new AutoValue_Category(name, alias, color);
    }

    public static Category create(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(AutoValue_Category.FirebaseValue.class).toAutoValue();
    }

    public Object toFirebaseValue() {
        return new AutoValue_Category.FirebaseValue(this);
    }

    public int getDrawableIcon() {
        String alias = alias();

        switch (alias) {
            case "animals":
                return R.drawable.ic_animals;
            case "games":
                return R.drawable.ic_games;
            case "films":
                return R.drawable.ic_films;
            case "bikes":
                return R.drawable.ic_bikes;
            case "science":
                return R.drawable.ic_science;
            case "sport":
                return R.drawable.ic_sport;
            case "goods":
                return R.drawable.ic_goods;
            case "school":
                return R.drawable.ic_school;
            case "video":
                return R.drawable.ic_video;
            default:
                return R.drawable.ic_star;
        }
    }

    public boolean isSchoolCategory() {
        return alias().equals("school");
    }
}