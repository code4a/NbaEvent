package com.code4a.retrofitutil.rx;

import com.code4a.retrofitutil.bean.ResponseBase;
import com.code4a.retrofitutil.exception.ApiException;
import com.code4a.retrofitutil.exception.ApiFailedHasData;
import com.code4a.retrofitutil.exception.ApiSuccessNoData;
import com.code4a.retrofitutil.utils.LogUtil;

import java.util.Iterator;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by code4a on 2017/5/26.
 */

public class RxHelper {

    static final String TAG = RxHelper.class.getSimpleName();

    /**
     * 对结果进行预处理
     *
     * @param rxSwitcher 异步变换器
     * @param <T> 需要变换的泛型
     * @param <R> 变换完成之后的泛型
     * @return 变换器
     */
    public static <T, R> Observable.Transformer<T, R> handleResult(final RxSwitcher<T, R> rxSwitcher) {
        return new Observable.Transformer<T, R>(){
            @Override
            public Observable<R> call(Observable<T> tObservable) {
                return tObservable.map(rxSwitcher)
                        .subscribeOn(Schedulers.computation())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对结果进行预处理
     *
     * @param <T> 泛型类
     * @return 变换器
     */
    public static <T> Observable.Transformer<ResponseBase<T>, T> handleResult() {
        return new Observable.Transformer<ResponseBase<T>, T>() {
            @Override
            public Observable<T> call(final Observable<ResponseBase<T>> tObservable) {
                return tObservable.flatMap(new Func1<ResponseBase<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseBase<T> result) {
                        LogUtil.e(TAG, result.getCode());
                        LogUtil.e(TAG, (result.getData() == null ? " data is null " : result.getData().getClass().getSimpleName()));
                        if (Integer.parseInt(result.getCode()) == 0) {
                            if (result.getData() == null) {
                                return Observable.error(new ApiSuccessNoData(result.getCode()));
                            }
                            return createData(result.getData());
                        } else {
                            if (result.getData() != null) {
                                ApiFailedHasData failedHasData = new ApiFailedHasData(result.getCode());
                                failedHasData.setObj(result.getData());
                                return Observable.error(failedHasData);
                            }
                            return Observable.error(new ApiException(result.getCode()));
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 转换成功的数据为被观察者
     *
     * @param data 数据
     * @param <T>  泛型类
     * @return 被观察者
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    LogUtil.e(TAG, " createData ");
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                    LogUtil.e(TAG, " createData onCompleted ");
                } catch (Exception e) {
                    LogUtil.e(TAG, " createData onError ");
                    subscriber.onError(e);
                }
            }
        });
    }

}
