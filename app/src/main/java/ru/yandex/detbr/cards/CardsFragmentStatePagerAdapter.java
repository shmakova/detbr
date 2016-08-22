package ru.yandex.detbr.cards;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.detbr.ui.fragments.CardFragmentBuilder;

/**
 * Created by shmakova on 22.08.16.
 */

public class CardsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Card> cards;

    public CardsFragmentStatePagerAdapter(FragmentManager fm, List<Card> cards) {
        super(fm);
        this.cards = cards;
    }

    @Override
    public Fragment getItem(int position) {
        return new CardFragmentBuilder(cards.get(position)).build();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

}