package ru.yandex.detbr.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;

/**
 * Created by shmakova on 21.08.16.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private final List<Card> cards;
    private final CardViewHolder.OnItemCLickListener onItemClickListener;

    public CardsAdapter(List<Card> cards, CardViewHolder.OnItemCLickListener onItemClickListener) {
        this.cards = cards;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_item, parent, false);
        return new CardViewHolder(convertView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card card = cards.get(position);
        Context context = holder.cover.getContext();

        Glide.with(context)
                .load(card.getCover())
                .centerCrop()
                .crossFade()
                .into(holder.cover);

        holder.title.setText(card.getTitle());
        holder.url.setText(card.getUrl());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cover)
        ImageView cover;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;

        private final OnItemCLickListener listener;

        public interface OnItemCLickListener {
            void onItemClick(int position);
        }

        CardViewHolder(View itemView, OnItemCLickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onItemClickListener;
        }

        @OnClick(R.id.card)
        void onCardItemClick() {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition());
            }
        }
    }
}
