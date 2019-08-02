package com.code4a.retrofitutil.rx;

import com.code4a.retrofitutil.bean.RespBase;
import com.code4a.retrofitutil.bean.RespBaseCI;
import com.code4a.retrofitutil.bean.RespBaseCS;
import com.code4a.retrofitutil.exception.ApiException;
import com.code4a.retrofitutil.exception.ApiFailedHasData;
import com.code4a.retrofitutil.exception.ApiSuccessNoData;
import com.code4a.retrofitutil.exception.ResponseCodeTypeException;
import com.code4a.retrofitutil.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by code4a on 2017/5/26.
 */

public class RxHelper {

    static final String TAG = RxHelper.class.getSimpleName();

    /**
     * 对结果进行预处理
     *
     * @param rxSwitcher 异步变换器
     * @param <T>        需要变换的泛型
     * @param <R>        变换完成之后的泛型
     * @return 变换器
     */
    public static <T, R> ObservableTransformer<T, R> handleResult(final RxSwitcher<T, R> rxSwitcher) {
        return new ObservableTransformer<T, R>() {
            @Override
            public ObservableSource<R> apply(Observable<T> upstream) {
                return upstream.map(rxSwitcher)
                        .subscribeOn(Schedulers.computation())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对结果进行预处理
     *
     * @param <T> 泛型类，转换后的范型结果
     * @return 变换器
     */
    public static <T> ObservableTransformer<RespBaseCI<T>, T> handleResultCI() {
        return new ObservableTransformer<RespBaseCI<T>, T>() {
            @Override
            public ObservableSource<T> apply(final Observable<RespBaseCI<T>> tObservable) {
                return tObservable.flatMap(new Function<RespBaseCI<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(RespBaseCI<T> result) {
                        String code = String.valueOf(result.getCode());
                        LogUtil.e(TAG, code);
                        LogUtil.e(TAG, (result.getData() == null ? " data is null " : result.getData().getClass().getSimpleName()));
                        if (Integer.parseInt(code) == 0) {
                            if (result.getData() == null) {
                                return Observable.error(new ApiSuccessNoData(code));
                            }
                            return createObservable(result.getData());
                        } else {
                            if (result.getData() != null) {
                                ApiFailedHasData failedHasData = new ApiFailedHasData(code);
                                failedHasData.setObj(result.getData());
                                return Observable.error(failedHasData);
                            }
                            return Observable.error(new ApiException(code));
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
     * 对结果进行预处理
     *
     * @param <T> 泛型类，转换后的范型结果
     * @return 变换器
     */
    public static <T> ObservableTransformer<RespBaseCS<T>, T> handleResultCS() {
        return new ObservableTransformer<RespBaseCS<T>, T>() {
            @Override
            public ObservableSource<T> apply(final Observable<RespBaseCS<T>> tObservable) {
                return tObservable.flatMap(new Function<RespBaseCS<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(RespBaseCS<T> result) {
                        String code = result.getCode();
                        LogUtil.e(TAG, code);
                        LogUtil.e(TAG, (result.getData() == null ? " data is null " : result.getData().getClass().getSimpleName()));
                        if (Integer.parseInt(code) == 0) {
                            if (result.getData() == null) {
                                return Observable.error(new ApiSuccessNoData(code));
                            }
                            return createObservable(result.getData());
                        } else {
                            if (result.getData() != null) {
                                ApiFailedHasData failedHasData = new ApiFailedHasData(code);
                                failedHasData.setObj(result.getData());
                                return Observable.error(failedHasData);
                            }
                            return Observable.error(new ApiException(code));
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
     * 对结果进行预处理
     *
     * @param <C> 响应码的类型，可能为整型，可能为字符串
     * @param <T> 泛型类，转换后的范型结果
     * @return 变换器
     */
    @Deprecated
    public static <C, T> ObservableTransformer<RespBase<C, T>, T> handleResult() {
        return new ObservableTransformer<RespBase<C, T>, T>() {
            @Override
            public ObservableSource<T> apply(final Observable<RespBase<C, T>> tObservable) {
                return tObservable.flatMap(new Function<RespBase<C, T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(RespBase<C, T> result) {
                        C c = result.getCode();
                        String code = null;
                        if (c instanceof Integer) {
                            code = String.valueOf(c);
                        } else if (c instanceof String) {
                            code = (String) c;
                        } else {
                            LogUtil.e(TAG, "响应码类型不符合标准");
                            return Observable.error(new ResponseCodeTypeException("response code type do not conform to standard"));
                        }
                        LogUtil.e(TAG, code);
                        LogUtil.e(TAG, (result.getData() == null ? " data is null " : result.getData().getClass().getSimpleName()));
                        if (Integer.parseInt(code) == 0) {
                            if (result.getData() == null) {
                                return Observable.error(new ApiSuccessNoData(code));
                            }
                            return createObservable(result.getData());
                        } else {
                            if (result.getData() != null) {
                                ApiFailedHasData failedHasData = new ApiFailedHasData(code);
                                failedHasData.setObj(result.getData());
                                return Observable.error(failedHasData);
                            }
                            return Observable.error(new ApiException(code));
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
    public static <T> Observable<T> createObservable(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception {
                try {
                    LogUtil.e(TAG, " createObservable ");
                    subscriber.onNext(data);
                    subscriber.onComplete();
                    LogUtil.e(TAG, " createObservable onCompleted ");
                } catch (Exception e) {
                    LogUtil.e(TAG, " createObservable onError ");
                    subscriber.onError(e);
                }
            }
        });
    }

}
