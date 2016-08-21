package ru.yandex.detbr.cards;

/**
 * Created by shmakova on 21.08.16.
 */

public class Card {
    private final String title;
    private final String url;
    private final String cover;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Card(String title, String url, String cover) {
        this.title = title;
        this.url = url;
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    @Override
    public String toString() {
        return "Card{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
