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

    static class ImageCardViewHolder extends CardViewHolder {
        @BindView(R.id.cover)
        ImageView cover;

        ImageCardViewHolder(View itemView, OnCardItemClickListener onCardItemClickListener) {
            super(itemView, onCardItemClickListener);
        }

        void bind(Card card) {
            super.bind(card);

            Context context = cover.getContext();

            Glide.with(context)
                    .load(card.getCover())
                    .centerCrop()
                    .crossFade()
                    .into(cover);
        }
    }
}