package ru.yandex.detbr.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.ui.fragments.CardFragmentBuilder;
import ru.yandex.detbr.ui.fragments.ImageCardFragmentBuilder;

/**
 * Created by shmakova on 22.08.16.
 */

public class CardsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private final List<Card> cards;

    public CardsFragmentStatePagerAdapter(FragmentManager fm, List<Card> cards) {
        super(fm);
        this.cards = cards;
    }

    @Override
    public Fragment getItem(int position) {
        Card card = cards.get(position);

        if (card.getCover() == null || card.getCover().isEmpty()) {
            return new CardFragmentBuilder(card).build();
        } else {
            return new ImageCardFragmentBuilder(card).build();
        }
    }

    @Override
    public int getCount() {
        return cards.size();
    }

}