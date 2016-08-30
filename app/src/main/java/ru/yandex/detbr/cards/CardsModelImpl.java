package ru.yandex.detbr.cards;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.detbr.categories.Category;
import ru.yandex.detbr.db.tables.CardsTable;

/**
 * Created by shmakova on 21.08.16.
 */


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class CardsModelImpl implements CardsModel {
    private final StorIOSQLite storIOSQLite;

    public CardsModelImpl(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }
    @Override
    public List<Card> getCardsListBySchool() {
        return storIOSQLite
                .get()
                .listOfObjects(Card.class)
                .withQuery(Query.builder()
                    .table(CardsTable.TABLE)
                    .build())
                .prepare()
                // TODO rx
                .executeAsBlocking();
    }

    @Override
    public List<Card> getFavouriteCards() {
        return storIOSQLite
                .get()
                .listOfObjects(Card.class)
                .withQuery(Query.builder()
                    .table(CardsTable.TABLE)
                    .where(CardsTable.COLUMN_LIKE + " = ?")
                    .whereArgs("1")
                    .build())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public List<Card> getCardsByCategory(Category category) {
        List<Card> cards = new ArrayList<>();

        switch (category.getTitle()) {
            case "Покемоны":
                cards.add(Card.builder()
                        .title("Играем в Pokémon Go на велосипеде")
                        .url("http://www.veloturist.org.ua/igraem-v-pokemon-go-na-velosipede/")
                        .cover("http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("КАК ИГРАТЬ В POKEMON GO")
                        .url("https://www.youtube.com/watch?v=tV9EErN3x-k")
                        .cover("http://img.youtube.com/vi/tV9EErN3x-k/0.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("5 МИРОВЫХ РЕКОРДОВ POKEMON GO")
                        .url("http://gopokemongo.ru/5-mirovyih-rekordov-pokemon-go.html")
                        .build());
                cards.add(Card.builder()
                        .title("О ЧИТАХ В POKEMON GO")
                        .url("http://gopokemongo.ru/o-chitah-v-pokemon-go.html")
                        .build());
                cards.add(Card.builder()
                        .title("Официальный сайт Pokemon")
                        .url("http://www.pokemon.com/ru/")
                        .build());
                cards.add(Card.builder()
                        .title("Pokemon: смотреть все серии")
                        .url("https://yandex.ru/video/search?text=покемоны&redircnt=1471710553.2")
                        .build());
                cards.add(Card.builder()
                        .title("Покедекс")
                        .url("http://www.pokemon.com/ru/pokedex/")
                        .build());
                break;
            case "Фильмы":
                cards.add(Card.builder()
                        .title("Бэтмен против Супермена: На заре справедливости")
                        .url("https://www.kinopoisk.ru/film/770631")
                        .cover("https://www.kinopoisk.ru/images/film_big/770631.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Варкрафт")
                        .url("https://www.kinopoisk.ru/film/277328")
                        .cover("https://www.kinopoisk.ru/images/film_big/277328.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Люди Икс: Апокалипсис")
                        .url("https://www.kinopoisk.ru/film/814016")
                        .cover("https://www.kinopoisk.ru/images/film_big/814016.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Алиса в Зазеркалье")
                        .url("https://www.kinopoisk.ru/film/723988")
                        .cover("https://www.kinopoisk.ru/images/film_big/723988.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Большой и добрый великан")
                        .url("https://www.kinopoisk.ru/film/840885")
                        .cover("https://www.kinopoisk.ru/images/film_big/840885.jpg")
                        .build());
                break;
            case "Велосипеды":
                cards.add(Card.builder()
                        .title("Как велогонщики Тур де Франс побеждают ветер")
                        .url("http://www.veloturist.org.ua/kak-velogonshhiki-tur-de-frans-pobezhdayut-veter/")
                        .build());
                cards.add(Card.builder()
                        .title("Играем в Pokémon Go на велосипеде")
                        .url("http://www.veloturist.org.ua/igraem-v-pokemon-go-na-velosipede/")
                        .cover("http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("10 велосипедов для подростков")
                        .url("https://market.yandex.ru/collections/10-velosipedov-dlja-podrostkov?track=fr_325422_snippet")
                        .build());
                cards.add(Card.builder()
                        .title("Выбираем велосипед для подростка")
                        .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                        .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Такие разные горные велосипеды")
                        .url("https://market.yandex.ru/articles/takie-raznye-gornye-velosipedy?track=fr_325422_snippet")
                        .build());
                break;
            default:
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
                break;
        }

        return cards;
    }
}
