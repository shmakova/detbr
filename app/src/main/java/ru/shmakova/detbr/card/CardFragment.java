package ru.shmakova.detbr.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.like.LikeButton;
import com.like.OnLikeListener;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.app.App;
import ru.shmakova.detbr.app.di.components.CardComponent;
import ru.shmakova.detbr.app.di.modules.CardModule;
import ru.shmakova.detbr.base.BaseMvpFragment;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.ui.listeners.OnCardsItemClickListener;

@FragmentWithArgs
public class CardFragment extends BaseMvpFragment<CardItemView, CardPresenter> implements CardItemView {
    @Arg
    int layoutResId;
    @Arg
    Card card;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.url)
    TextView url;
    @BindView(R.id.like_btn)
    LikeButton likeButton;
    @Nullable
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.favicon)
    ImageView favicon;
    @Nullable
    @BindView(R.id.description)
    TextView description;
    @Nullable
    @BindView(R.id.text_wrapper)
    View textWrapper;
    @BindView(R.id.card)
    CardView cardView;

    private OnCardsItemClickListener onCardsItemClickListener;
    private CardComponent cardComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        injectDependencies();
        return inflater.inflate(layoutResId, container, false);
    }

    private void injectDependencies() {
        cardComponent = App.get(getContext()).applicationComponent().plus(new CardModule());
        cardComponent.inject(this);
    }

    @Override
    public CardPresenter createPresenter() {
        return cardComponent.presenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                presenter.onLikeClick(card);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                presenter.onLikeClick(card);
            }
        });
        presenter.loadCard(card);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnCardsItemClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnCardsItemClickListener.class.getName());
        }

        onCardsItemClickListener = (OnCardsItemClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCardsItemClickListener = null;
        card = null;
    }

    @OnClick(R.id.card)
    public void onCardClick() {
        if (onCardsItemClickListener != null) {
            onCardsItemClickListener.onCardsItemClick(card);
        }
    }

    @OnLongClick(R.id.card)
    public boolean onLongCardClick() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, card.url());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
        return true;
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setLike(boolean like) {
        likeButton.setLiked(like);
    }

    @Override
    public void setDescription(String description) {
        this.description.setVisibility(View.VISIBLE);
        this.description.setText(description);
    }

    @Override
    public void setFavicon(String favicon) {
        this.favicon.setVisibility(View.VISIBLE);
        Glide.with(getActivity())
                .load(card.favicon())
                .centerCrop()
                .crossFade()
                .into(this.favicon);
    }

    @Override
    public void setSite(String site) {
        url.setText(site);
    }

    @Override
    public void setImage(String url) {
        if (image != null) {
            Glide.with(getActivity())
                    .load(card.image())
                    .centerCrop()
                    .crossFade()
                    .into(image);
        }
    }

    @Override
    public void setBackgroundColor(String color) {
        cardView.setCardBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void setWhiteText() {
        title.setTextColor(ContextCompat.getColor(getContext(), R.color.light_transparent_white));
        url.setTextColor(ContextCompat.getColor(getContext(), R.color.transparent_white));
        url.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        likeButton.setUnlikeDrawableRes(R.drawable.ic_like_white_border);

        if (textWrapper != null) {
            textWrapper.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_black));
        }

        if (description != null) {
            description.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }
}
