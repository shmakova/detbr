package ru.yandex.detbr.ui.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.utils.UrlUtils;

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
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull Card item, @NonNull CardViewHolder viewHolder) {
        viewHolder.bind(item);
    }

    @Override
    protected boolean isForViewType(@NonNull Card item, List<Card> items, int position) {
        return item.getCover() == null || item.getCover().isEmpty();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
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
            url.setText(UrlUtils.getHost(card.getUrl()));
            likeButton.setChecked(card.getLike());
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