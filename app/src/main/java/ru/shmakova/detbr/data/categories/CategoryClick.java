package ru.shmakova.detbr.data.categories;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CategoryClick {
    public abstract Category category();

    public abstract int x();

    public abstract int y();

    public static CategoryClick create(Category category, int x, int y) {
        return new AutoValue_CategoryClick(category, x, y);
    }
}
