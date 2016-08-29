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

    private final OnCardItemClickListener onCardItemClickListener;

    public ImageCardAdapterDelegate(OnCardItemClickListener onCardItemClickListener) {
        this.onCardItemClickListener = onCardItemClickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull Card item, List<Card> items, int position) {
        return !item.getCover().isEmpty();
    }

    @NonNull
    @Override
    public ImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ImageCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_card, parent, false), onCardItemClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull Card item, @NonNull ImageCardViewHolder viewHolder) {
        viewHolder.bind(item);
    }

    static class ImageCardViewHolder extends RecyclerView.ViewHolder {
        private final OnCardItemClickListener listener;

        @BindView(R.id.cover)
        ImageView cover;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.like_btn)
        CheckBox likeButton;

        ImageCardViewHolder(View itemView, OnCardItemClickListener onCardItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onCardItemClickListener;

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
        void onCardItemClick() {
            if (listener != null) {
                listener.onCardItemClick(getAdapterPosition());
            }
        }
    }
}