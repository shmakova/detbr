package ru.shmakova.detbr.presentation.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.shmakova.detbr.data.cards.Card;

/**
 * Created by shmakova on 28.08.16.
 */

public interface FavoritesView extends MvpLceView<List<Card>> {
    void showEmptyView();
}
