package ru.shmakova.detbr.base;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {
    @NonNull
    private final CompositeSubscription subscriptionsToUnsubscribeOnUnbindView = new CompositeSubscription();

    protected final void unsubscribeOnUnbindView(@NonNull Subscription subscription, @NonNull Subscription... subscriptions) {
        subscriptionsToUnsubscribeOnUnbindView.add(subscription);

        for (Subscription s : subscriptions) {
            subscriptionsToUnsubscribeOnUnbindView.add(s);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        subscriptionsToUnsubscribeOnUnbindView.clear();
    }
}
