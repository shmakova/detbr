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

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.di.components.CategoriesComponent;
import ru.yandex.detbr.di.modules.CategoriesModule;
import ru.yandex.detbr.ui.adapters.CategoriesAdapter;
import ru.yandex.detbr.ui.animators.CustomLceAnimator;
import ru.yandex.detbr.ui.presenters.CategoriesPresenter;
import ru.yandex.detbr.ui.views.CategoriesView;
import ru.yandex.detbr.utils.ErrorMessageDeterminer;

/**
 * Created by shmakova on 24.08.16.
 */

@FragmentWithArgs
public class CategoriesFragment extends BaseLceFragment<FrameLayout, List<Category>, CategoriesView, CategoriesPresenter>
        implements CategoriesView {
    @Arg(required = false)
    Category category;

    @Inject
    ErrorMessageDeterminer errorMessageDeterminer;

    @BindView(R.id.categories_list)
    RecyclerView categories;

    private OnCategoriesItemClickListener onCategoriesItemClickListener;
    private CategoriesComponent categoriesComponent;
    private CategoriesAdapter adapter;

    public interface OnCategoriesItemClickListener {
        void onCategoriesItemClick(Category category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = categoriesComponent.adapter();
        categories.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        categories.setLayoutManager(linearLayoutManager);
    }

    private void injectDependencies() {
        categoriesComponent = App.get(getContext()).applicationComponent().plus(new CategoriesModule());
        categoriesComponent.inject(this);
    }

    @NonNull
    @Override
    public LceViewState<List<Category>, CategoriesView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Category> getData() {
        return (adapter == null) ? null : adapter.getCategories();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return errorMessageDeterminer.getErrorMessage(e, pullToRefresh);
    }

    @NonNull
    @Override
    public CategoriesPresenter createPresenter() {
        return categoriesComponent.presenter();
    }

    @Override
    public void setData(List<Category> data) {
        if (adapter != null) {
            adapter.setCategories(data);
            adapter.notifyDataSetChanged();
            presenter.onCategoryClick(adapter.getPositionClicks());
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadCategories(category, pullToRefresh);
    }

    @Override
    public void showCategoryCards(Category category) {
        if (onCategoriesItemClickListener != null) {
            onCategoriesItemClickListener.onCategoriesItemClick(category);
        }
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
    protected void animateContentViewIn() {
        CustomLceAnimator.showContent(loadingView, contentView, errorView);
    }

    @Override
    protected void animateLoadingViewIn() {
        CustomLceAnimator.hideLoading(loadingView, contentView, errorView);
    }
}
