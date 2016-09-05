package ru.yandex.detbr.data.repository.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;

import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

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
}