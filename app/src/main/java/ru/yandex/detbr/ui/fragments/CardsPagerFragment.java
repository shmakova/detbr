package ru.yandex.detbr.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.categories.CategoryClick;
import ru.yandex.detbr.di.components.CardsComponent;
import ru.yandex.detbr.di.modules.CardsModule;
import ru.yandex.detbr.presentation.presenters.CardsPresenter;
import ru.yandex.detbr.presentation.views.CardsView;
import ru.yandex.detbr.ui.adapters.CardsFragmentStatePagerAdapter;
import ru.yandex.detbr.ui.adapters.SchoolsFragmentStatePagerAdapter;
import ru.yandex.detbr.ui.animators.BackgroundAnimator;
import ru.yandex.detbr.ui.animators.CustomLceAnimator;
import ru.yandex.detbr.ui.other.CarouselPageTransformer;
import ru.yandex.detbr.utils.ErrorMessageDeterminer;

/**
 * Created by shmakova on 24.08.16.
 */

public class CardsPagerFragment extends BaseLceFragment<FrameLayout, List<Card>, CardsView, CardsPresenter>
        implements CardsView,
        CategoriesFragment.OnCategorySelectedListener,
        SchoolsFragment.OnSchoolClickListener,
        LastCardFragment.OnBackButtonClickListener {
    private static final int PAGE_LIMIT = 7;

    @Inject
    ErrorMessageDeterminer errorMessageDeterminer;

    @BindView(R.id.cards_pager)
    ViewPager cardsPager;
    @BindView(R.id.category_cards_backward)
    View categoryCardsBackward;
    @BindView(R.id.previous_category_cards_backward)
    View previousCardsBackward;
    @BindView(R.id.divider)
    View divider;

    protected FragmentManager fragmentManager;
    @NonNull
    private CardsComponent cardsComponent;

    @NonNull
    @Override
    public CardsPresenter createPresenter() {
        return cardsComponent.presenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        injectDependencies();
        fragmentManager = getChildFragmentManager();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cards, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardsPager.setOffscreenPageLimit(PAGE_LIMIT);
        cardsPager.setPageMargin(-getResources().getDimensionPixelOffset(R.dimen.card_padding));
        cardsPager.setPageTransformer(false, new CarouselPageTransformer());

        if (savedInstanceState == null) {
            CategoriesFragment categoriesFragment = new CategoriesFragmentBuilder().build();
            if (fragmentManager != null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.categories_frame_layout, categoriesFragment)
                        .commit();
            }
        }
    }

    private void injectDependencies() {
        cardsComponent = App.get(getContext()).applicationComponent().plus(new CardsModule());
        cardsComponent.inject(this);
    }

    @Override
    public void setData(List<Card> cards) {
        cardsPager.setAdapter(new CardsFragmentStatePagerAdapter(fragmentManager, cards, presenter.isFirstLoad()));
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

    @Override
    public void setBackgroundColor(String color, int x, int y) {
        BackgroundAnimator.setCategoryCardsBackwardBackgroundColor(
                categoryCardsBackward,
                previousCardsBackward,
                Color.parseColor(color), x, y);
    }

    @Override
    public void setBackgroundColor(@ColorRes int color, int x, int y) {
        BackgroundAnimator.setCategoryCardsBackwardBackgroundColor(
                categoryCardsBackward,
                previousCardsBackward,
                ContextCompat.getColor(getContext(), color), x, y);
    }

    @Override
    public void setDividerColor(@ColorRes int color) {
        divider.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    @Override
    public void showSchoolFragment() {
        cardsPager.setAdapter(new SchoolsFragmentStatePagerAdapter(fragmentManager));
    }

    @Override
    public void onCategorySelected(CategoryClick categoryClick) {
        presenter.onCategorySelected(categoryClick);
    }

    @Override
    public void onSchoolClick() {
        presenter.refresh();
    }

    @Override
    public void onBackButtonClick() {
        cardsPager.setCurrentItem(0, true);
    }
}
