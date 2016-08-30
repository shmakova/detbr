package ru.yandex.detbr.cards;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    protected OnCardClickListener listener;

    public CardAdapterDelegate(Activity activity, OnCardClickListener onCardClickListener) {
        inflater = activity.getLayoutInflater();
        listener = onCardClickListener;
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
        viewHolder.bind(card);
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.like_btn)
        CheckBox likeButton;

        private final OnCardClickListener listener;

        CardViewHolder(View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onCardClickListener;
        }

        void bind(Card card) {
            title.setText(card.getTitle());
            Uri uri = Uri.parse(card.getUrl());
            url.setText(uri.getHost());
            likeButton.setChecked(card.getLike());
        }

        @OnClick(R.id.card)
        void onCardItemClick() {
            if (listener != null) {
                listener.onCardItemClick(getAdapterPosition());
            }
        }
        
        @OnClick(R.id.like_btn)
        void onLikeClick() {
            if (listener != null) {
                listener.onLikeClick(getAdapterPosition());
            }
        }
    }
}