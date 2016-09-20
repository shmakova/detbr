package ru.yandex.detbr.ui.delegates;

import android.support.annotation.NonNull;
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
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;

/**
 * Created by shmakova on 25.08.16.
 */


public class ImageCardAdapterDelegate extends AbsListItemAdapterDelegate<Card, Card, ImageCardAdapterDelegate.ImageCardViewHolder> {

    private final OnCardClickListener onCardClickListener;

    public ImageCardAdapterDelegate(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull Card item, List<Card> items, int position) {
        return item.image() != null && !item.image().isEmpty();
    }

    @NonNull
    @Override
    public ImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ImageCardViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_favorite_image_card, parent, false), onCardClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull Card item, @NonNull ImageCardViewHolder viewHolder) {
        viewHolder.bind(item);
    }

    static class ImageCardViewHolder extends RecyclerView.ViewHolder {
        private final OnCardClickListener listener;

        @BindView(R.id.image)
        ImageView cover;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.like_btn)
        LikeButton likeButton;

        ImageCardViewHolder(View itemView, OnCardClickListener onCardClickListener) {
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
            if (card.dark()) {
                likeButton.setUnlikeDrawableRes(R.drawable.ic_like_white_border);
            } else {
                likeButton.setUnlikeDrawableRes(R.drawable.ic_like_black_border);
            }

            title.setText(card.title());
            url.setText(card.getSiteName());
            likeButton.setLiked(card.like());

            Glide.with(cover.getContext())
                    .load(card.image())
                    .centerCrop()
                    .crossFade()
                    .into(cover);
        }

        @OnClick(R.id.card)
        void onCardClick() {
            if (listener != null) {
                listener.onCardClick(getAdapterPosition());
            }
        }
    }
}