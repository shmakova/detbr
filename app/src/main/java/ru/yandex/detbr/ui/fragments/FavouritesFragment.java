package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.cards.Category;
import ru.yandex.detbr.ui.adapters.CardsAdapter;
import ru.yandex.detbr.ui.presenters.CardsPresenter;
import ru.yandex.detbr.ui.views.CardsView;

/**
 * Created by shmakova on 21.08.16.
 */

public class FavouritesFragment extends BaseFragment implements CardsView {
    @Inject
    CardsPresenter presenter;

    @BindView(R.id.cards_list)
    RecyclerView recyclerView;

    private OnCardsItemClickListener onCardsItemClickListener;

    public interface OnCardsItemClickListener {
        void onCardsItemClick(Card card);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().cardsComponent().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.bindView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        presenter.loadFavouriteCards();
    }

    @Override
    public void setCardsData(List<Card> cards) {
        CardsAdapter cardsAdapter = new CardsAdapter(cards, (position) -> {
            Card card = cards.get(position);

            if (onCardsItemClickListener != null) {
                onCardsItemClickListener.onCardsItemClick(card);
            }
        });
        recyclerView.setAdapter(cardsAdapter);
    }

    @Override
    public void setCategories(List<Category> categories) {
        // no categories yet
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
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
    }
}
