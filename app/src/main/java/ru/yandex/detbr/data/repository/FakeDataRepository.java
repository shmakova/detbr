package ru.yandex.detbr.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.repository.models.Category;
import rx.Observable;

/**
 * Created by shmakova on 28.08.16.
 */

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class FakeDataRepository implements DataRepository {
    @Override
    public Observable<List<Category>> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder()
                .title("Покемоны")
                .backgroundColor("#f2dd50")
                .build());
        categories.add(Category.builder()
                .title("Фильмы")
                .backgroundColor("#4aa3fd")
                .build());
        categories.add(Category.builder().title("Вещи")
                .backgroundColor("#e86302")
                .build());
        categories.add(Category.builder().title("Мультфильмы")
                .backgroundColor("#cc79ff")
                .build());
        return Observable.just(categories);
    }

    @Override
    public Observable<List<Card>> getCardsListBySchool() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.builder()
                .title("КАК ИГРАТЬ В POKEMON GO")
                .url("https://www.youtube.com/watch?v=oglUmHzAqyQ")
                .cover("http://img.youtube.com/vi/tV9EErN3x-k/0.jpg")
                .build());
        cards.add(Card.builder()
                .title("LEGO Angry Birds")
                .url("http://www.lego.com/ru-ru/angrybirdsmovie?icmp=CORUFRAngryBirds")
                .cover("http://cache.lego.com/r/www/-/media/catalogs/themes/franchises/theme%20cards%202016/angrybirds.jpg")
                .build());
        cards.add(Card.builder()
                .title("Тайная жизнь домашних животных")
                .url("https://www.kinopoisk.ru/film/743088")
                .cover("https://www.kinopoisk.ru/images/film_big/743088.jpg")
                .build());
        cards.add(Card.builder()
                .title("Лонгборд Penny Original 22\"")
                .url("https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls")
                .cover("https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg")
                .build());
        cards.add(Card.builder()
                .title("Ледниковый период: Столкновение неизбежно")
                .url("https://www.kinopoisk.ru/film/818145")
                .cover("https://www.kinopoisk.ru/images/film_big/818145.jpg")
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
                .title("10 иллюстраций о том, каким видят мир творческие люди")
                .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                .build());
        cards.add(Card.builder()
                .title("Смотреть Гравити Фолз")
                .url("https://yandex.ru/video/search?text=гравити%20фолз")
                .cover("http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg")
                .build());
        cards.add(Card.builder()
                .title("Смотреть Смешарики Пин Код")
                .url("https://yandex.ru/video/search?text=смешарики%20пин%20код")
                .cover("http://www.tvzavr.ru/common/tvzstatic/cache/644x363/16662.jpg")
                .build());
        cards.add(Card.builder()
                .title("Как велогонщики Тур де Франс побеждают ветер")
                .url("http://www.veloturist.org.ua/kak-velogonshhiki-tur-de-frans-pobezhdayut-veter/")
                .build());
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
                .title("Играем в Pokémon Go на велосипеде")
                .url("http://www.veloturist.org.ua/igraem-v-pokemon-go-na-velosipede/")
                .cover("http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg")
                .build());
        cards.add(Card.builder()
                .title("15 крутых дизайнерских вещиц, с которыми жизнь станет приятнее")
                .url("https://www.adme.ru/tvorchestvo-dizajn/15-krutyh-dizajnerskih-veschic-s-kotorymi-zhizn-stanet-priyatnee-1152260/")
                .cover("https://files4.adme.ru/files/news/part_115/1152260/preview-16564065-650x341-98-1471597471.jpg")
                .build());
        cards.add(Card.builder()
                .title("25 безумно милых штуковин, которые каждый захочет иметь в своем доме")
                .url("https://files4.adme.ru/files/news/part_94/945860/preview-25651565-650x341-98-1471442066.jpg")
                .cover("http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg")
                .build());
        cards.add(Card.builder()
                .title("Тест: Сможете ли вы узнать логотипы известных брендов?")
                .url("https://www.adme.ru/tvorchestvo-reklama/test-smozhete-li-vy-uznat-logotipy-izvestnyh-brendov-1302515/")
                .build());
        cards.add(Card.builder()
                .title("Тест: Правильно ли вы произносите названия известных брендов?")
                .url("https://www.adme.ru/tvorchestvo-dizajn/test-pravilno-li-vy-proiznosite-nazvaniya-izvestnyh-brendov-1081510/")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
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
        cards.add(Card.builder()
                .title("Большой и добрый великан")
                .url("https://www.kinopoisk.ru/film/840885")
                .cover("https://www.kinopoisk.ru/images/film_big/840885.jpg")
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
        cards.add(Card.builder()
                .title("В поисках Дори")
                .url("https://www.kinopoisk.ru/film/692957")
                .cover("https://www.kinopoisk.ru/images/film_big/692957.jpg")
                .build());
        return Observable.just(cards);
    }

    @Override
    public Observable<List<Card>> getFavouriteCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.builder()
                .title("Смотреть Гравити Фолз")
                .url("https://yandex.ru/video/search?text=гравити%20фолз")
                .cover("http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("10 иллюстраций о том, каким видят мир творческие люди")
                .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("Невероятные оптические иллюзии от профессора математики")
                .url("https://lifehacker.ru/2016/07/04/kokichi-sugihara-illusion/")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("11 комиксов о том, как изменилась наша жизнь с появлением интернета")
                .url("https://www.adme.ru/zhizn-nostalgiya/11-komiksov-o-tom-kak-izmenilas-nasha-zhizn-s-poyavleniem-interneta-1299765/")
                .cover("https://files3.adme.ru/files/news/part_129/1299765/10455565-810-1000-53c212670e-1470661968.jpg")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("Лонгборд Penny Original 22\"")
                .url("https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls")
                .cover("https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("10 велосипедов для подростков")
                .url("https://market.yandex.ru/collections/10-velosipedov-dlja-podrostkov?track=fr_325422_snippet")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("Выбираем велосипед для подростка")
                .url("https://market.yandex.ru/articles/vybiraem-velosiped-dlja-podrostka?track=fr_325422_snippet")
                .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                .like(true)
                .build());
        cards.add(Card.builder()
                .title("Такие разные горные велосипеды")
                .url("https://market.yandex.ru/articles/takie-raznye-gornye-velosipedy?track=fr_325422_snippet")
                .like(true)
                .build());
        return Observable.just(cards);
    }

    @Override
    public Observable<List<Card>> getCardsByCategory(Category category) {
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
                        .url("https://www.youtube.com/watch?v=oglUmHzAqyQ")
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
            case "Мультфильмы":
                cards.add(Card.builder()
                        .title("Смотреть Гравити Фолз")
                        .url("https://yandex.ru/video/search?text=гравити%20фолз")
                        .cover("http://theheroes.ru/wp-content/uploads/2016/02/gravityfalls.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Смотреть Смешарики Пин Код")
                        .url("https://yandex.ru/video/search?text=смешарики%20пин%20код")
                        .cover("http://www.tvzavr.ru/common/tvzstatic/cache/644x363/16662.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Ледниковый период: Столкновение неизбежно")
                        .url("https://www.kinopoisk.ru/film/818145")
                        .cover("https://www.kinopoisk.ru/images/film_big/818145.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("В поисках Дори")
                        .url("https://www.kinopoisk.ru/film/692957")
                        .cover("https://www.kinopoisk.ru/images/film_big/692957.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Тайная жизнь домашних животных")
                        .url("https://www.kinopoisk.ru/film/743088")
                        .cover("https://www.kinopoisk.ru/images/film_big/743088.jpg")
                        .build());
                break;
            case "Вещи":
                cards.add(Card.builder()
                        .title("15 крутых дизайнерских вещиц, с которыми жизнь станет приятнее")
                        .url("https://www.adme.ru/tvorchestvo-dizajn/15-krutyh-dizajnerskih-veschic-s-kotorymi-zhizn-stanet-priyatnee-1152260/")
                        .cover("https://files4.adme.ru/files/news/part_115/1152260/preview-16564065-650x341-98-1471597471.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("25 безумно милых штуковин, которые каждый захочет иметь в своем доме")
                        .url("https://files4.adme.ru/files/news/part_94/945860/preview-25651565-650x341-98-1471442066.jpg")
                        .cover("http://www.veloturist.org.ua/wp-content/uploads/2016/08/mari-senn-igraet-v-pokemon-go-na-563x353.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Тест: Сможете ли вы узнать логотипы известных брендов?")
                        .url("https://www.adme.ru/tvorchestvo-reklama/test-smozhete-li-vy-uznat-logotipy-izvestnyh-brendov-1302515/")
                        .build());
                cards.add(Card.builder()
                        .title("Тест: Правильно ли вы произносите названия известных брендов?")
                        .url("https://www.adme.ru/tvorchestvo-dizajn/test-pravilno-li-vy-proiznosite-nazvaniya-izvestnyh-brendov-1081510/")
                        .cover("https://cs-ellpic.yandex.net/cms_resources/navigation/pages/42467/rdt056c6k40ksqvq36kmd8ra2o_720x540@x1.jpg")
                        .build());
                cards.add(Card.builder()
                        .title("Лонгборд Penny Original 22\"")
                        .url("https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls")
                        .cover("https://mdata.yandex.net/i?path=b0517140217_img_id1704454932646973526.jpeg")
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

        return Observable.just(cards);
    }

    @Override
    public Observable<List<String>> getSchoolsList() {
        List<String> schools = new ArrayList<>();
        schools.add("ГБОУ г. Москвы лицей №1535");
        schools.add("ГБОУ г. Москвы центр образования №57 «Пятьдесят седьмая школа»");
        schools.add("ГБОУ г. Москвы «Многопрофильный лицей №1501»");
        schools.add("ГБОУ г. Москвы лицей «Вторая школа»");
        schools.add("ГБОУ г. Москвы «Школа-интернат «Интеллектуал»");
        return Observable.just(schools);
    }
}
