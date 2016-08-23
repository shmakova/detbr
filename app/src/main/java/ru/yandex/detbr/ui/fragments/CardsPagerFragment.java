package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import ru.yandex.detbr.ui.adapters.CardsFragmentStatePagerAdapter;
import ru.yandex.detbr.ui.adapters.CategoriesAdapter;
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
    ViewPager cardsPager;
    @BindView(R.id.categories_list)
    RecyclerView categories;

    private FragmentManager fragmentManager;

    private OnCategoriesItemClickListener onCategoriesItemClickListener;

    public interface OnCategoriesItemClickListener {
        void onCategoriesItemClick(Category category);
    }

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        categories.setLayoutManager(linearLayoutManager);
        presenter.loadCards();
        presenter.loadCategories();
    }

    @Override
    public void setCardsData(List<Card> cards) {
        cardsPager.setAdapter(new CardsFragmentStatePagerAdapter(fragmentManager, cards));
        cardsPager.setOffscreenPageLimit(PAGE_LIMIT);
    }

    @Override
    public void setCategories(List<Category> categoriesList) {
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoriesList, (position) -> {
            Category category = categoriesList.get(position);

            if (onCategoriesItemClickListener != null) {
                onCategoriesItemClickListener.onCategoriesItemClick(category);
            }
        });

        categories.setAdapter(categoriesAdapter);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnCategoriesItemClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnCategoriesItemClickListener.class.getName());
        }

        onCategoriesItemClickListener = (OnCategoriesItemClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCategoriesItemClickListener = null;
    }
}
