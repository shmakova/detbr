package ru.yandex.detbr.presentation.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.yandex.detbr.data.repository.models.Card;

/**
 * Created by shmakova on 28.08.16.
 */

public interface FavoritesView extends MvpLceView<List<Card>> {
    void showEmptyView();
}
