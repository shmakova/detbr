package ru.yandex.detbr.cards;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import ru.yandex.detbr.R;

/**
 * Created by shmakova on 25.08.16.
 */


public class ImageCardAdapterDelegate extends CardAdapterDelegate {

    public ImageCardAdapterDelegate(Activity activity, OnCardItemClickListener onCardItemClickListener) {
        super(activity, onCardItemClickListener);
    }

    @Override
    public boolean isForViewType(@NonNull List<Card> items, int position) {
        String cover = items.get(position).getCover();
        return !(cover == null || cover.isEmpty());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ImageCardViewHolder(inflater.inflate(R.layout.item_image_card, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Card> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder) {
        ImageCardViewHolder viewHolder = (ImageCardViewHolder) holder;
        Card card = items.get(position);
        Context context = viewHolder.cover.getContext();

        Glide.with(context)
                .load(card.getCover())
                .centerCrop()
                .crossFade()
                .into(viewHolder.cover);

        viewHolder.title.setText(card.getTitle());
        viewHolder.url.setText(card.getUrl());
    }

    static class ImageCardViewHolder extends CardViewHolder {
        @BindView(R.id.cover)
        ImageView cover;

        ImageCardViewHolder(View itemView, OnCardItemClickListener onCardItemClickListener) {
            super(itemView, onCardItemClickListener);
        }
    }
}