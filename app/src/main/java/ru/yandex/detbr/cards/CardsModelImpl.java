package ru.yandex.detbr.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsModelImpl implements CardsModel {
    @Override
    public List<Card> getCardsListBySchool() {
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
        cards.add(new Card(
                "Играем в Pokémon Go на велосипеде",
                "http://www.veloturist.org.ua/igraem-v-pokemon-go-na-velosipede/",
                "http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg"));
        cards.add(new Card(
                "Выбираем велосипед для подростка",
                "https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet",
                "https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg"));
        cards.add(new Card(
                "10 иллюстраций о том, каким видят мир творческие люди",
                "https://www.adme.ru/tvorchestvo-hudozhniki/10-illyustracij-o-tom-kakim-vidyat-mir-tvorcheskie-lyudi-1316415/",
                "https://files7.adme.ru/files/news/part_131/1316415/12235665-1-1469454901-650-1ccd54f71a-1-1469456115.jpg"));
        cards.add(new Card(
                "11 комиксов о том, как изменилась наша жизнь с появлением интернета",
                "https://www.adme.ru/zhizn-nostalgiya/11-komiksov-o-tom-kak-izmenilas-nasha-zhizn-s-poyavleniem-interneta-1299765/",
                "https://files3.adme.ru/files/news/part_129/1299765/10455565-810-1000-53c212670e-1470661968.jpg"));
        cards.add(new Card(
                "Лонгборд Penny Original 22\"",
                "https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls",
                "https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg"));
        cards.add(new Card(
                "Невероятные оптические иллюзии от профессора математики",
                "https://lifehacker.ru/2016/07/04/kokichi-sugihara-illusion/",
                "https://lifehacker.ru/wp-content/uploads/2016/07/topVH_1467364830.jpg"));
        cards.add(new Card(
                "Смотреть Гравити Фолз",
                "https://yandex.ru/video/search?text=гравити%20фолз",
                "http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg"));
        return cards;
    }

    @Override
    public List<Card> getFavouriteCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(
                "11 комиксов о том, как изменилась наша жизнь с появлением интернета",
                "https://www.adme.ru/zhizn-nostalgiya/11-komiksov-o-tom-kak-izmenilas-nasha-zhizn-s-poyavleniem-interneta-1299765/",
                "https://files3.adme.ru/files/news/part_129/1299765/10455565-810-1000-53c212670e-1470661968.jpg"));
        cards.add(new Card(
                "Лонгборд Penny Original 22\"",
                "https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls",
                "https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg"));
        cards.add(new Card(
                "Невероятные оптические иллюзии от профессора математики",
                "https://lifehacker.ru/2016/07/04/kokichi-sugihara-illusion/",
                "https://lifehacker.ru/wp-content/uploads/2016/07/topVH_1467364830.jpg"));
        cards.add(new Card(
                "Смотреть Гравити Фолз",
                "https://yandex.ru/video/search?text=гравити%20фолз",
                "http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg"));
        return cards;
    }
}