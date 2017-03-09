package ru.shmakova.detbr.card;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.data.cards.Card;

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
        LikeButton likeButton;
        @BindView(R.id.card)
        CardView cardView;
        @BindView(R.id.favicon)
        ImageView favicon;

        private final OnCardClickListener listener;

        CardViewHolder(View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onCardClickListener;
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    listener.onLikeClick(getAdapterPosition());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    listener.onLikeClick(getAdapterPosition());
                }
            });
        }

        void bind(Card card) {
            title.setText(card.title());
            url.setText(card.getSiteName());

            if (card.dark()) {
                title.setTextColor(ContextCompat.getColor(title.getContext(), R.color.white));
                url.setTextColor(ContextCompat.getColor(url.getContext(), R.color.transparent_white));
                url.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                likeButton.setUnlikeDrawableRes(R.drawable.ic_like_white_border);
            } else {
                title.setTextColor(ContextCompat.getColor(title.getContext(), R.color.darkest_transparent));
                url.setTextColor(ContextCompat.getColor(url.getContext(), R.color.dark_transparent));
                url.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                likeButton.setUnlikeDrawableRes(R.drawable.ic_like_black_border);
            }

            likeButton.setLiked(card.like());

            if (card.color() == null) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(title.getContext(), R.color.white));
            } else {
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
    }
}
