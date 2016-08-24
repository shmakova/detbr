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
import ru.yandex.detbr.categories.Category;
import ru.yandex.detbr.ui.adapters.CategoriesAdapter;
import ru.yandex.detbr.ui.presenters.CategoriesPresenter;
import ru.yandex.detbr.ui.views.CategoriesView;

/**
 * Created by shmakova on 24.08.16.
 */

public class CategoriesFragment extends BaseFragment implements CategoriesView {
    @Inject
    CategoriesPresenter presenter;

    @BindView(R.id.categories_list)
    RecyclerView categories;

    private OnCategoriesItemClickListener onCategoriesItemClickListener;

    public interface OnCategoriesItemClickListener {
        void onCategoriesItemClick(Category category);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().categoriesComponent().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.bindView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        categories.setLayoutManager(linearLayoutManager);
        presenter.loadCategories();
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

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }
}
