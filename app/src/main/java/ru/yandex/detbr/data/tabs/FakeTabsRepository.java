package ru.yandex.detbr.data.tabs;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.detbr.data.tabs.models.Tab;
import rx.Observable;

/**
 * Created by shmakova on 29.08.16.
 */

public class FakeTabsRepository implements TabsRepository {
    private List<Tab> tabs = new ArrayList<>();

    public FakeTabsRepository() {
        tabs = new ArrayList<>();
        tabs.add(Tab.builder()
                .url("https://www.kinopoisk.ru/film/743088/")
                .title("Тайная жизнь домашних животных (2016) — отзывы — КиноПоиск")
                .build());
        tabs.add(Tab.builder()
                .url("https://yandex.ru/search/?lr=213&msid=1472502552.83919.22905.22493&text=%D0%BB%D0%B5%D1%82%D0%BE")
                .title("Лето — Яндекс: нашлось 230 млн результатов")
                .build());
        tabs.add(Tab.builder()
                .url("https://market.yandex.ru/product/10821104?hid=91577&track=cms_bestsell_artcls")
                .title("Купить лонгборд Penny Original 22\" — выгодные цены на Яндекс.Маркете")
                .build());
        tabs.add(Tab.builder()
                .url("https://yandex.ru/search/?lr=213&msid=1472502791.96778.22895.18102&text=%D0%BC%D0%B0%D0%B9%D0%BD%D0%BA%D1%80%D0%B0%D1%84%D1%82")
                .title("майнкрафт — Яндекс: нашлось 84 млн результатов")
                .build());
    }

    @Override
    public Observable<List<Tab>> getSavedTabs() {
        return Observable.just(tabs);
    }

    @Override
    public void addTab(Tab tab) {
        tabs.add(0, tab);
    }

    @Override
    public void removeTab(Tab tab) {
        tabs.remove(tab);
    }

    @Override
    public void removeLastTab() {
        tabs.remove(0);
    }
}
