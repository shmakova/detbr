package ru.yandex.detbr.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsModelImpl implements CardsModel {
    @Override
    public List<Card> getCardsList() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(
                "Тайная жизнь домашних животных",
                "https://www.kinopoisk.ru/film/743088/",
                "https://www.kinopoisk.ru/images/film_big/743088.jpg"));
        cards.add(new Card(
                "КАК ИГРАТЬ В POKEMON GO",
                "https://www.youtube.com/watch?v=tV9EErN3x-k",
                "http://img.youtube.com/vi/tV9EErN3x-k/0.jpg"));
        cards.add(new Card(
                "LEGO Angry Birds",
                "http://www.lego.com/ru-ru/angrybirdsmovie?icmp=CORUFRAngryBirds",
                "http://cache.lego.com/r/www/-/media/catalogs/themes/franchises/theme%20cards%202016/angrybirds.jpg"));
        return cards;
    }
}
