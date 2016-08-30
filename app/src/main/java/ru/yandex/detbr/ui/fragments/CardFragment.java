package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.ui.presenters.CardsPresenter;

/**
 * Created by shmakova on 22.08.16.
 */

@FragmentWithArgs
public class CardFragment extends BaseFragment {
    @Arg
    Card card;
    @Inject
    CardsPresenter presenter;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.url)
    TextView url;
    @BindView(R.id.like_btn)
    CheckBox likeButton;

    private OnCardsItemClickListener onCardsItemClickListener;

    public interface OnCardsItemClickListener {
        void onCardsItemClick(Card card);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        App.get(getContext()).applicationComponent().cardsComponent().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (card != null) {
            title.setText(card.getTitle());
            Uri uri = Uri.parse(card.getUrl());
            url.setText(uri.getHost());
            likeButton.setChecked(card.getLike());
        }
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

    @OnClick(R.id.like_btn)
    public void onLikeButtonClick() {
        if (card != null) {
            presenter.changeLike(card.getUrl());
        }
    }
}
