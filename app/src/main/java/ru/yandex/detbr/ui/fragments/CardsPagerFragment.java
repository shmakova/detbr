package ru.yandex.detbr.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by shmakova on 21.08.16.
 */

public class CardsPagerFragment extends BaseFragment implements CardsView {
    private static final int PAGE_LIMIT = 7;

    @Inject
    CardsPresenter presenter;

    @BindView(R.id.cards_pager)
    ViewPager viewPager;

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().cardsComponent().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cards, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.bindView(this);
        fragmentManager = getChildFragmentManager();
        presenter.loadCards();
    }

    @Override
    public void setCardsData(List<Card> cards) {
        viewPager.setAdapter(new CardsFragmentStatePagerAdapter(fragmentManager, cards));
        viewPager.setOffscreenPageLimit(PAGE_LIMIT);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }
}
