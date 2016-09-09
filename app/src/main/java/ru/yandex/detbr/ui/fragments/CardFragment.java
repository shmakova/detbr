package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.di.components.CardComponent;
import ru.yandex.detbr.di.modules.CardModule;
import ru.yandex.detbr.presentation.presenters.CardPresenter;
import ru.yandex.detbr.presentation.views.CardItemView;
import ru.yandex.detbr.ui.listeners.OnCardsItemClickListener;

/**
 * Created by shmakova on 22.08.16.
 */

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
    CheckBox likeButton;
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

    @OnClick(R.id.like_btn)
    public void onLikeButtonClick() {
        presenter.onLikeClick(card);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setLike(boolean like) {
        likeButton.setChecked(like);
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
        likeButton.setButtonDrawable(ContextCompat.getDrawable(getContext(), R.drawable.like_white));

        if (textWrapper != null) {
            textWrapper.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_black));
        }

        if (description != null) {
            description.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }
}
