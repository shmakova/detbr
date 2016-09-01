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

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.ui.listeners.OnCardsItemClickListener;
import ru.yandex.detbr.ui.listeners.OnLikeClickListener;

/**
 * Created by shmakova on 22.08.16.
 */

@FragmentWithArgs
public class CardFragment extends BaseFragment {
    @Arg
    Card card;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.url)
    TextView url;
    @BindView(R.id.like_btn)
    CheckBox likeButton;

    private OnCardsItemClickListener onCardsItemClickListener;
    private OnLikeClickListener onLikeClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
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
            // Todo перенести в модель
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

        if (!(getActivity() instanceof OnLikeClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnLikeClickListener.class.getName());
        }

        onCardsItemClickListener = (OnCardsItemClickListener) getActivity();
        onLikeClickListener = (OnLikeClickListener) getActivity();
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
        if (onLikeClickListener != null) {
            onLikeClickListener.onLikeClick(card);
        }
    }
}
