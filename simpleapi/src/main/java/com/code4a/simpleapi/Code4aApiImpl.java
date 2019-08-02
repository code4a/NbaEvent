package com.code4a.simpleapi;

import android.content.Context;
import android.util.Log;
import com.code4a.retrofitutil.engine.RetrofitManager;
import com.code4a.retrofitutil.rx.RxHelper;
import com.code4a.retrofitutil.rx.RxSubscriber;
import com.code4a.retrofitutil.utils.HttpUtil;
import com.code4a.retrofitutil.utils.TransferDataType;
import com.code4a.simpleapi.simple.ContentBoard;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by code4a on 2017/4/7.
 */

public class Code4aApiImpl implements Code4aApi {

    private static final String TAG = Code4aApiImpl.class.getSimpleName();

    RetrofitManager retrofitManager;

    public Code4aApiImpl(Context context) {
        retrofitManager = new RetrofitManager.Builder()
                .setContext(context)
                .setBaseUrl(SimpleApi.CODE4A_API)
                .enableCache()
                .setTimeoutSec(15)
                .setTransferDataType(TransferDataType.JSON)
                .setHttpHeaderMap(HttpUtil.getJsonHeaderMap())
                .build();
    }

    Code4aService createCode4aService() {
        return retrofitManager.create(Code4aService.class);
    }

    @Override
    public void testHttp() {
        createCode4aService().getAlipayInfo(CODE4A_ALIPAYINFO)
                .compose(RxHelper.<String, String>handleResult())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onCompleted ---> : xxxxxxxxxxxxxx ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError ---> : " + e);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String result) {
                        Log.e(TAG, "result : " + result);
                    }
                });
    }

    @Override
    public void getContentBoard(RxSubscriber<ContentBoard> rxSubscriber) {
        retrofitManager
                .doSubscribe(createCode4aService()
                .getContentBoard(CONTENT_BOARD), rxSubscriber);
    }

}
