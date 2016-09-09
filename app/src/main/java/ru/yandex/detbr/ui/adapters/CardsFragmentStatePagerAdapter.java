package ru.yandex.detbr.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.ui.fragments.CardFragmentBuilder;

/**
 * Created by shmakova on 22.08.16.
 */

public class CardsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Card> cards;

    public CardsFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
        cards = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Card card = cards.get(position);
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

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

}