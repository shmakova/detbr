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
        cards.add(Card.builder()
                .title("Тайная жизнь домашних животных")
                .url("https://www.kinopoisk.ru/film/743088/")
                .cover("https://www.kinopoisk.ru/images/film_big/743088.jpg")
                .build());
        cards.add(Card.builder()
                .title("КАК ИГРАТЬ В POKEMON GO")
                .url("https://www.youtube.com/watch?v=tV9EErN3x-k")
                .cover("http://img.youtube.com/vi/tV9EErN3x-k/0.jpg")
                .build());
        cards.add(Card.builder()
                .title("LEGO Angry Birds")
                .url("http://www.lego.com/ru-ru/angrybirdsmovie?icmp=CORUFRAngryBirds")
                .cover("http://cache.lego.com/r/www/-/media/catalogs/themes/franchises/theme%20cards%202016/angrybirds.jpg")
                .build());
        cards.add(Card.builder()
                .title("Играем в Pokémon Go на велосипеде")
                .url("http://www.veloturist.org.ua/igraem-v-pokemon-go-na-velosipede/")
                .cover("http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg")
                .build());
        cards.add(Card.builder()
                .title("Выбираем велосипед для подростка")
                .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                .build());
        cards.add(Card.builder()
                .title("Лонгборд Penny Original 22\"")
                .url("https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls")
                .cover("https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg")
                .build());
        cards.add(Card.builder()
                .title("10 иллюстраций о том, каким видят мир творческие люди")
                .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                .build());
        cards.add(Card.builder()
                .title("Невероятные оптические иллюзии от профессора математики")
                .url("https://lifehacker.ru/2016/07/04/kokichi-sugihara-illusion/")
                .cover("https://lifehacker.ru/wp-content/uploads/2016/07/topVH_1467364830.jpg")
                .build());
        cards.add(Card.builder()
                .title("11 комиксов о том, как изменилась наша жизнь с появлением интернета")
                .url("https://www.adme.ru/zhizn-nostalgiya/11-komiksov-o-tom-kak-izmenilas-nasha-zhizn-s-poyavleniem-interneta-1299765/")
                .cover("https://files3.adme.ru/files/news/part_129/1299765/10455565-810-1000-53c212670e-1470661968.jpg")
                .build());
        cards.add(Card.builder()
                .title("Смотреть Гравити Фолз")
                .url("https://yandex.ru/video/search?text=гравити%20фолз")
                .cover("http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg")
                .build());
        return cards;
    }

    @Override
    public List<Card> getFavouriteCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.builder()
                .title("Лонгборд Penny Original 22\"")
                .url("https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls")
                .cover("https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg")
                .build());
        cards.add(Card.builder()
                .title("10 иллюстраций о том, каким видят мир творческие люди")
                .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                .build());
        cards.add(Card.builder()
                .title("Невероятные оптические иллюзии от профессора математики")
                .url("https://lifehacker.ru/2016/07/04/kokichi-sugihara-illusion/")
                .cover("https://lifehacker.ru/wp-content/uploads/2016/07/topVH_1467364830.jpg")
                .build());
        cards.add(Card.builder()
                .title("11 комиксов о том, как изменилась наша жизнь с появлением интернета")
                .url("https://www.adme.ru/zhizn-nostalgiya/11-komiksov-o-tom-kak-izmenilas-nasha-zhizn-s-poyavleniem-interneta-1299765/")
                .cover("https://files3.adme.ru/files/news/part_129/1299765/10455565-810-1000-53c212670e-1470661968.jpg")
                .build());
        cards.add(Card.builder()
                .title("Смотреть Гравити Фолз")
                .url("https://yandex.ru/video/search?text=гравити%20фолз")
                .cover("http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg")
                .build());
        return cards;
    }
}
