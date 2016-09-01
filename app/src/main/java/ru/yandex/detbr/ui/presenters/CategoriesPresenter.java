package ru.yandex.detbr.ui.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ru.yandex.detbr.data.repository.DataRepository;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.ui.views.CategoriesView;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shmakova on 24.08.16.
 */

public class CategoriesPresenter extends BaseRxPresenter<CategoriesView, List<Category>> {
    @NonNull
    private final DataRepository dataRepository;
    private final CompositeSubscription compositeSubscription;

    @Inject
    public CategoriesPresenter(@NonNull DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        compositeSubscription = new CompositeSubscription();
    }

    public void loadCategories(Category category, boolean pullToRefresh) {
        Observable<List<Category>> observable = dataRepository
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