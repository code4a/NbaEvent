package com.code4a.retrofitutil.rx;

import com.code4a.retrofitutil.exception.ApiException;
import com.code4a.retrofitutil.exception.ApiFailedHasData;
import com.code4a.retrofitutil.exception.ApiSuccessNoData;
import com.code4a.retrofitutil.exception.ResponseCodeTypeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by code4a on 2017/5/27.
 */

public abstract class RxSubscriber<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onFailed(((ApiException) e).getCode());
            onComplete();
        } else if (e instanceof ApiSuccessNoData) {
            onSuccess(null);
            onComplete();
        } else if (e instanceof ApiFailedHasData) {
            onFailed(((ApiFailedHasData) e).getObj());
            onComplete();
        } else if (e instanceof ResponseCodeTypeException) {
            onFailed(e.getMessage());
            onComplete();
        } else {
            onException(e);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(Object obj);

    public abstract void onException(Throwable e);

}
