package ru.shmakova.detbr.cards;

import android.support.annotation.ColorRes;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.shmakova.detbr.data.cards.Card;

public interface CardsView extends MvpLceView<List<Card>> {
    void setBackgroundColor(@ColorRes int color, @ColorRes int dividerColor, int x, int y);

    void setBackgroundColor(String color, @ColorRes int dividerColor, int x, int y);
}
