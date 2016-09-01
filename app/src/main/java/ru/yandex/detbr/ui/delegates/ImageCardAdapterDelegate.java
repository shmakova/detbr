package ru.yandex.detbr.ui.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
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
import ru.yandex.detbr.data.repository.models.Card;

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
        return !item.getCover().isEmpty();
    }

    @NonNull
    @Override
    public ImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ImageCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_card, parent, false), onCardClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull Card item, @NonNull ImageCardViewHolder viewHolder) {
        viewHolder.bind(item);
    }

    static class ImageCardViewHolder extends RecyclerView.ViewHolder {
        private final OnCardClickListener listener;

        @BindView(R.id.cover)
        ImageView cover;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.like_btn)
        CheckBox likeButton;

        ImageCardViewHolder(View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onCardClickListener;

        }

        void bind(Card card) {
            title.setText(card.getTitle());
            url.setText(card.getUrl());
            likeButton.setChecked(card.getLike());

            Context context = cover.getContext();

            Glide.with(context)
                    .load(card.getCover())
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

        @OnClick(R.id.like_btn)
        void onLikeClick() {
            if (listener != null) {
                listener.onLikeClick(getAdapterPosition());
            }
        }
    }
}