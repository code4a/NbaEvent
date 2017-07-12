package com.code4a.retrofitutil.rx;

import com.code4a.retrofitutil.exception.ApiException;
import com.code4a.retrofitutil.exception.ApiFailedHasData;
import com.code4a.retrofitutil.exception.ApiSuccessNoData;

import rx.Subscriber;

/**
 * Created by code4a on 2017/5/27.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onFailed(((ApiException) e).getCode());
            onCompleted();
        } else if (e instanceof ApiSuccessNoData) {
            onSuccess(null);
            onCompleted();
        } else if (e instanceof ApiFailedHasData) {
            onFailed(((ApiFailedHasData) e).getObj());
            onCompleted();
        } else {
            onException(e);
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(Object obj);

    public abstract void onException(Throwable e);

}
