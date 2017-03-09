package ru.shmakova.detbr.categories;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ru.shmakova.detbr.base.BaseRxPresenter;
import ru.shmakova.detbr.data.categories.CategoriesRepository;
import ru.shmakova.detbr.data.categories.Category;
import ru.shmakova.detbr.data.categories.CategoryClick;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class CategoriesPresenter extends BaseRxPresenter<CategoriesView, List<Category>> {
    private final CompositeSubscription compositeSubscription;
    @NonNull
    private final CategoriesRepository categoriesRepository;

    @Inject
    public CategoriesPresenter(@NonNull CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
        compositeSubscription = new CompositeSubscription();
    }

    public void loadCategories(Category category, boolean pullToRefresh) {
        Observable<List<Category>> observable = categoriesRepository
                .getCategories()
                .map(categories -> {
                    categories.remove(category);
                    return categories;
                });
        subscribe(observable, pullToRefresh);
    }

    public void onCategoryClick(Observable<CategoryClick> positionClicks) {
        if (isViewAttached()) {
            compositeSubscription.add(
                    positionClicks.subscribe(categoryClick -> getView().showCategoryCards(categoryClick)));
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
