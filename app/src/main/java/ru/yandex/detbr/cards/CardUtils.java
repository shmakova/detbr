package ru.yandex.detbr.cards;


import android.support.annotation.Nullable;

public class CardUtils {
    public static Card getCard(String title, String url, @Nullable String cover, boolean like) {
        return Card.builder()
                .title(title)
                .url(url)
                .cover(cover)
                .like(like)
                .build();
    }
}
