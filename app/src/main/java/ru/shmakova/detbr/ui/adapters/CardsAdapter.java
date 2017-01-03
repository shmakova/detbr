package ru.shmakova.detbr.ui.adapters;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;

import java.util.List;

import javax.inject.Inject;

import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.ui.delegates.CardAdapterDelegate;
import ru.shmakova.detbr.ui.delegates.ImageCardAdapterDelegate;
import ru.shmakova.detbr.ui.delegates.OnCardClickListener;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsAdapter extends ListDelegationAdapter<List<Card>> {
    @Inject
    public CardsAdapter(OnCardClickListener onCardItemClickListener) {
        delegatesManager.addDelegate(new CardAdapterDelegate(onCardItemClickListener));
        delegatesManager.addDelegate(new ImageCardAdapterDelegate(onCardItemClickListener));
    }
}
