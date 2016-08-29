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
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.di.components.FavoritesComponent;
import ru.yandex.detbr.di.modules.FavoritesModule;
import ru.yandex.detbr.ui.adapters.CardsAdapter;
import ru.yandex.detbr.ui.presenters.FavoritesPresenter;
import ru.yandex.detbr.ui.views.FavoritesView;
import ru.yandex.detbr.utils.ErrorMessageDeterminer;

/**
 * Created by shmakova on 21.08.16.
 */

public class FavoritesFragment extends BaseLceFragment<FrameLayout, List<Card>, FavoritesView, FavoritesPresenter> implements FavoritesView {
    public interface OnCardsItemClickListener {
        void onCardsItemClick(Card card);
    }

    @Inject
    ErrorMessageDeterminer errorMessageDeterminer;

    @BindView(R.id.cards_list)
    RecyclerView recyclerView;

    private FavoritesComponent favoritesComponent;
    private CardsAdapter adapter;
    private OnCardsItemClickListener onCardsItemClickListener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favoritesComponent = App.get(getContext()).applicationComponent().plus(new FavoritesModule());
        favoritesComponent.inject(this);
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @NonNull
    @Override
    public FavoritesPresenter createPresenter() {
        return favoritesComponent.presenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
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

    @NonNull
    @Override
    public LceViewState<List<Card>, FavoritesView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Card> getData() {
        return adapter.getItems();
    }

    @Override
    public void setData(List<Card> data) {
        adapter = new CardsAdapter((position) -> {
            Card card = data.get(position);

            if (onCardsItemClickListener != null) {
                onCardsItemClickListener.onCardsItemClick(card);
            }
        });
        adapter.setItems(data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadCards(pullToRefresh);
    }
}
