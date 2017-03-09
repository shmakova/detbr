package ru.shmakova.detbr.cards;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;

import java.util.List;

import javax.inject.Inject;

import ru.shmakova.detbr.card.CardAdapterDelegate;
import ru.shmakova.detbr.card.ImageCardAdapterDelegate;
import ru.shmakova.detbr.card.OnCardClickListener;
import ru.shmakova.detbr.data.cards.Card;

public class CardsAdapter extends ListDelegationAdapter<List<Card>> {
    @Inject
    public CardsAdapter(OnCardClickListener onCardItemClickListener) {
        delegatesManager.addDelegate(new CardAdapterDelegate(onCardItemClickListener));
        delegatesManager.addDelegate(new ImageCardAdapterDelegate(onCardItemClickListener));
    }
}
