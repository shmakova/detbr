package ru.yandex.detbr.categories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmakova on 24.08.16.
 */

public class CategoriesModelImpl implements CategoriesModel {
    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder()
                .title("Покемоны")
                .cover("http://books.mercado.by/wp-content/uploads/2012/07/pikachu-wallpaper-510.png")
                .backgroundColor("#E9CE05")
                .build());
        categories.add(Category.builder()
                .title("Фильмы")
                .backgroundColor("#5CB4DC")
                .cover("http://iconspot.ru/files/322337.png")
                .build());
        categories.add(Category.builder()
                .title("Велосипеды")
                .cover("http://iconspot.ru/files/107227.png")
                .backgroundColor("#FFB940")
                .build());
        categories.add(Category.builder().title("Статьи").build());
        categories.add(Category.builder().title("Вещи").build());
        categories.add(Category.builder().title("Образование").build());
        categories.add(Category.builder().title("Спорт").build());
        categories.add(Category.builder().title("Игры").build());
        categories.add(Category.builder().title("Мультфильмы").build());
        categories.add(Category.builder().title("Животные").build());
        return categories;
    }
}
