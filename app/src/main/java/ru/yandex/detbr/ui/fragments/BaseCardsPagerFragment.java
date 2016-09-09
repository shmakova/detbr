package ru.yandex.detbr.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.di.components.CardsComponent;
import ru.yandex.detbr.di.modules.CardsModule;
import ru.yandex.detbr.presentation.presenters.CardsPresenter;
import ru.yandex.detbr.presentation.views.CardsView;
import ru.yandex.detbr.ui.adapters.CardsFragmentStatePagerAdapter;
import ru.yandex.detbr.ui.animators.CustomLceAnimator;
import ru.yandex.detbr.ui.other.CarouselPageTransformer;
import ru.yandex.detbr.utils.ErrorMessageDeterminer;

/**
 * Created by shmakova on 24.08.16.
 */

public class BaseCardsPagerFragment extends BaseLceFragment<FrameLayout, List<Card>, CardsView, CardsPresenter> implements CardsView {
    private static final int PAGE_LIMIT = 7;

    @Inject
    ErrorMessageDeterminer errorMessageDeterminer;

    @BindView(R.id.cards_pager)
    ViewPager cardsPager;

    protected FragmentManager fragmentManager;
    @NonNull
    private CardsComponent cardsComponent;
    private CardsFragmentStatePagerAdapter adapter;

    @NonNull
    @Override
    public CardsPresenter createPresenter() {
        return cardsComponent.presenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CardsFragmentStatePagerAdapter(fragmentManager);
        cardsPager.setAdapter(adapter);
        cardsPager.setOffscreenPageLimit(PAGE_LIMIT);
        cardsPager.setPageMargin(-getResources().getDimensionPixelOffset(R.dimen.card_padding));
        cardsPager.setClipChildren(false);

        CarouselPageTransformer carouselPageTransformer = new CarouselPageTransformer();
        cardsPager.setPageTransformer(false, carouselPageTransformer);
    }

    private void injectDependencies() {
        cardsComponent = App.get(getContext()).applicationComponent().plus(new CardsModule());
        cardsComponent.inject(this);
    }

    @Override
    public void setData(List<Card> cards) {
        if (adapter != null) {
            adapter.setCards(cards);
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadCards(pullToRefresh);
    }

    @NonNull
    @Override
    public LceViewState<List<Card>, CardsView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Card> getData() {
        return ((CardsFragmentStatePagerAdapter) cardsPager.getAdapter()).getCards();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return errorMessageDeterminer.getErrorMessage(e, pullToRefresh);
    }

    @Override
    protected void animateContentViewIn() {
        CustomLceAnimator.showContent(loadingView, contentView, errorView);
    }

    @Override
    protected void animateLoadingViewIn() {
        CustomLceAnimator.showLoading(loadingView, contentView, errorView);
    }
}
