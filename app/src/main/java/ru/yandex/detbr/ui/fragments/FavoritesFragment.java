package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.di.components.FavoritesComponent;
import ru.yandex.detbr.di.modules.FavoritesModule;
import ru.yandex.detbr.di.modules.NavigationModule;
import ru.yandex.detbr.presentation.presenters.FavoritesPresenter;
import ru.yandex.detbr.presentation.views.FavoritesView;
import ru.yandex.detbr.ui.activities.MainActivity;
import ru.yandex.detbr.ui.adapters.CardsAdapter;
import ru.yandex.detbr.ui.delegates.OnCardClickListener;
import ru.yandex.detbr.ui.listeners.OnCardsItemClickListener;
import ru.yandex.detbr.utils.ErrorMessageDeterminer;

/**
 * Created by shmakova on 21.08.16.
 */

public class FavoritesFragment extends BaseLceFragment<FrameLayout, List<Card>, FavoritesView, FavoritesPresenter> implements FavoritesView {
    @Inject
    ErrorMessageDeterminer errorMessageDeterminer;

    @BindView(R.id.cards_list)
    RecyclerView recyclerView;
    @BindView(R.id.empty_favorites)
    View emptyView;

    private FavoritesComponent favoritesComponent;
    private CardsAdapter adapter;
    private OnCardsItemClickListener onCardsItemClickListener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    private void injectDependencies() {
        favoritesComponent = App.get(getContext()).applicationComponent().plus(
                new FavoritesModule(),
                new NavigationModule((MainActivity) getActivity()));
        favoritesComponent.inject(this);
    }

    @NonNull
    @Override
    public FavoritesPresenter createPresenter() {
        return favoritesComponent.presenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return errorMessageDeterminer.getErrorMessage(e, pullToRefresh);
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
        return (adapter == null) ? null : adapter.getItems();
    }

    @Override
    public void setData(List<Card> cards) {
        adapter = new CardsAdapter(new OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                Card card = cards.get(position);

                if (onCardsItemClickListener != null) {
                    onCardsItemClickListener.onCardsItemClick(card);
                }
            }

            @Override
            public void onLikeClick(int position) {
                Card card = cards.get(position);
                presenter.onLikeClick(card);
            }
        });
        adapter.setItems(cards);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadCards(pullToRefresh);
    }

    @Override
    public void showEmptyView() {
        contentView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }
}
