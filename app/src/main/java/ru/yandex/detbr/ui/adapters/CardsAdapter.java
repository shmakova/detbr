package ru.yandex.detbr.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import java.util.List;

import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.cards.CardAdapterDelegate;
import ru.yandex.detbr.cards.ImageCardAdapterDelegate;
import ru.yandex.detbr.cards.OnCardClickListener;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsAdapter extends RecyclerView.Adapter {
    private final List<Card> cards;
    private final AdapterDelegatesManager<List<Card>> delegatesManager;

    public CardsAdapter(Activity activity, List<Card> cards, OnCardClickListener onCardClickListener) {
        this.cards = cards;
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new CardAdapterDelegate(activity, onCardClickListener));
        delegatesManager.addDelegate(new ImageCardAdapterDelegate(activity, onCardClickListener));
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(cards, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(cards, position, holder);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
