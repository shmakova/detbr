package ru.yandex.detbr.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.ui.fragments.CardFragmentBuilder;
import ru.yandex.detbr.ui.fragments.IntroCardFragmentBuilder;
import ru.yandex.detbr.ui.fragments.LastCardFragment;

/**
 * Created by shmakova on 22.08.16.
 */

public class CardsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private final List<Card> cards;
    private final boolean firstLoad;

    public CardsFragmentStatePagerAdapter(FragmentManager fm, List<Card> cards, boolean firstLoad) {
        super(fm);
        this.cards = cards;
        this.firstLoad = firstLoad;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == getCount() - 1) {
            return new LastCardFragment();
        }
        Card card;

        if (firstLoad) {
            if (position == 0) {
                return new IntroCardFragmentBuilder(R.layout.fragment_intro_first).build();
            } else if (position == 1) {
                return new IntroCardFragmentBuilder(R.layout.fragment_intro_second).build();
            } else {
                card = cards.get(position - 2);
            }
        } else {
            card = cards.get(position);
        }

        String type = card.type() == null || card.type().isEmpty() ? Card.TEXT_TYPE : card.type();
        switch (type) {
            case "text":
                return new CardFragmentBuilder(card, R.layout.item_card).build();
            case "plain_image":
                return new CardFragmentBuilder(card, R.layout.item_plain_image_card).build();
            case "full_image":
                return new CardFragmentBuilder(card, R.layout.item_full_image_card).build();
            default:
                return new CardFragmentBuilder(card, R.layout.item_card).build();
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int getCount() {
        return cards.size() + (firstLoad? 3 : 1);
    }
}