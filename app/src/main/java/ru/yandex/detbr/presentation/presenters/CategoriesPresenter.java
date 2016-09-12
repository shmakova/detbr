package ru.yandex.detbr.presentation.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ru.yandex.detbr.data.categories.CategoriesRepository;
import ru.yandex.detbr.data.categories.Category;
import ru.yandex.detbr.presentation.views.CategoriesView;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shmakova on 24.08.16.
 */

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

    public void onCategoryClick(Observable<Category> positionClicks) {
        if (isViewAttached()) {
            compositeSubscription.add(
                    positionClicks.subscribe(category -> getView().showCategoryCards(category)));
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