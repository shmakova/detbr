package ru.yandex.detbr.ui.delegates;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;

/**
 * Created by shmakova on 25.08.16.
 */


public class CardAdapterDelegate extends AbsListItemAdapterDelegate<Card, Card, CardAdapterDelegate.CardViewHolder> {
    private final OnCardClickListener listener;

    public CardAdapterDelegate(OnCardClickListener onCardClickListener) {
        listener = onCardClickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_card, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull Card item, @NonNull CardViewHolder viewHolder) {
        viewHolder.bind(item);
    }

    @Override
    protected boolean isForViewType(@NonNull Card item, List<Card> items, int position) {
        return item.image() == null || item.image().isEmpty();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.like_btn)
        CheckBox likeButton;
        @BindView(R.id.card)
        CardView cardView;
        @BindView(R.id.favicon)
        ImageView favicon;

        private final OnCardClickListener listener;

        CardViewHolder(View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onCardClickListener;
        }

        void bind(Card card) {
            title.setText(card.title());
            url.setText(card.getSiteName());
            likeButton.setChecked(card.like());

            if (card.dark()) {
                title.setTextColor(ContextCompat.getColor(title.getContext(), R.color.white));
                url.setTextColor(ContextCompat.getColor(url.getContext(), R.color.transparent_url_color));
                likeButton.setButtonDrawable(ContextCompat.getDrawable(likeButton.getContext(), R.drawable.like_white));
            } else {
                title.setTextColor(ContextCompat.getColor(title.getContext(), R.color.transparent_text_color));
                url.setTextColor(ContextCompat.getColor(url.getContext(), R.color.transparent_text_color));
                likeButton.setButtonDrawable(ContextCompat.getDrawable(likeButton.getContext(), R.drawable.like_black));
            }

            if (card.color() != null) {
                cardView.setCardBackgroundColor(Color.parseColor(card.color()));
            }

            Glide.with(favicon.getContext())
                    .load(card.favicon())
                    .centerCrop()
                    .crossFade()
                    .into(favicon);
        }

        @OnClick(R.id.card)
        void onCardClick() {
            if (listener != null) {
                listener.onCardClick(getAdapterPosition());
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