package ru.yandex.detbr.ui.adapters;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;

import java.util.List;

import javax.inject.Inject;

import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.ui.delegates.CardAdapterDelegate;
import ru.yandex.detbr.ui.delegates.ImageCardAdapterDelegate;
import ru.yandex.detbr.ui.delegates.OnCardClickListener;

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
