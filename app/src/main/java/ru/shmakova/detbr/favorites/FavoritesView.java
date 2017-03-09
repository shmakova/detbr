package ru.shmakova.detbr.favorites;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.shmakova.detbr.data.cards.Card;

public interface FavoritesView extends MvpLceView<List<Card>> {
    void showEmptyView();
}
