package ru.yandex.detbr.cards;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.R;

/**
 * Created by shmakova on 25.08.16.
 */


public class CardAdapterDelegate implements AdapterDelegate<List<Card>> {

    protected LayoutInflater inflater;
    protected OnCardItemClickListener listener;

    public CardAdapterDelegate(Activity activity, OnCardItemClickListener onCardItemClickListener) {
        inflater = activity.getLayoutInflater();
        listener = onCardItemClickListener;
    }

    @Override
    public boolean isForViewType(@NonNull List<Card> items, int position) {
        String cover = items.get(position).getCover();
        return cover == null || cover.isEmpty();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new CardViewHolder(inflater.inflate(R.layout.item_card, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Card> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder) {

        CardViewHolder viewHolder = (CardViewHolder) holder;
        Card card = items.get(position);

        viewHolder.title.setText(card.getTitle());
        viewHolder.url.setText(card.getUrl());
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;

        private final OnCardItemClickListener listener;

        CardViewHolder(View itemView, OnCardItemClickListener onCardItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onCardItemClickListener;
        }

        @OnClick(R.id.card)
        void onCardItemClick() {
            if (listener != null) {
                listener.onCardItemClick(getAdapterPosition());
            }
        }
    }
}