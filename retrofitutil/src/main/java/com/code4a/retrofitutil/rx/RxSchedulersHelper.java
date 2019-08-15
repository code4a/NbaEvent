package com.code4a.retrofitutil.rx;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiang on 2017/11/2.
 */

public class RxSchedulersHelper {

    /**
     * 将一个集合变为被观察者
     *
     * @param iterable 数据集合
     * @param <T>      数据类型
     * @return 被观察者
     */
    public static <T> Observable<T> changeDataList(Iterable<? extends T> iterable) {
        return Observable.fromIterable(iterable);
    }

    /**
     * 数据变换操作
     *
     * @param iterable   数据集合
     * @param rxSwitcher 数据异步变换器
     * @param subscriber 观察者
     * @param <T>        被变换的泛型
     * @param <R>        变换结果的泛型
     *
     */
    public static <T, R> void changeDataList(Iterable<? extends T> iterable, RxSwitcher<T, R> rxSwitcher, Observer<R> subscriber) {
        changeDataList(iterable)
                .compose(RxHelper.handleResult(rxSwitcher))
                .subscribe(subscriber);
    }

    /**
     * 数据传输过程中，保存数据库
     *
     * @param iterable         数据集合
     * @param rxSqlComputation 数据存储接口
     * @param subscriber       观察者
     * @param <T>              被存储数据的泛型
     *
     */
    public static <T> void saveDataListToSql(Iterable<? extends T> iterable, RxSqlComputation<T> rxSqlComputation, Observer<T> subscriber) {
        Observable.fromIterable(iterable)
                .subscribeOn(Schedulers.io())
                .doOnNext(rxSqlComputation)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
