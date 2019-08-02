package com.app.baseapp.baseui;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class BaseRepository  {


    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();


    protected void addSubscription(DisposableObserver subscription) {
        mCompositeSubscription.add(subscription);
    }

    protected void removeSubscription(DisposableObserver subscription) {
        mCompositeSubscription.remove(subscription);
    }


    protected void onCleared() {
        mCompositeSubscription.clear();
    }
}
