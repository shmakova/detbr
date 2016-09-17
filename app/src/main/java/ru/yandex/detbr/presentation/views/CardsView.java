package ru.yandex.detbr.presentation.views;

import android.support.annotation.ColorRes;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.yandex.detbr.data.cards.Card;

/**
 * Created by shmakova on 21.08.16.
 */

public interface CardsView extends MvpLceView<List<Card>> {
    void setBackgroundColor(@ColorRes int color, @ColorRes int dividerColor, int x, int y);

    void setBackgroundColor(String color, @ColorRes int dividerColor, int x, int y);

    void showSchoolFragment();
}
