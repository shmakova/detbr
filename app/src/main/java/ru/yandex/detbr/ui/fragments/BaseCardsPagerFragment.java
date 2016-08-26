package ru.yandex.detbr.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.ui.adapters.CardsFragmentStatePagerAdapter;
import ru.yandex.detbr.ui.presenters.CardsPresenter;
import ru.yandex.detbr.ui.views.CardsView;

/**
 * Created by shmakova on 24.08.16.
 */

public class BaseCardsPagerFragment extends BaseFragment implements CardsView {
    private static final int PAGE_LIMIT = 7;

    @Inject
    CardsPresenter presenter;

    @BindView(R.id.cards_pager)
    ViewPager cardsPager;

    protected FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().cardsComponent().inject(this);
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public void setCardsData(List<Card> cards) {
        cardsPager.setAdapter(new CardsFragmentStatePagerAdapter(fragmentManager, cards));
        cardsPager.setOffscreenPageLimit(PAGE_LIMIT);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }
}
