package com.code4a.simpleapi;

import com.code4a.retrofitutil.rx.RxSubscriber;
import com.code4a.simpleapi.simple.ContentBoard;

import rx.Subscription;

/**
 * Created by code4a on 2017/4/7.
 */

public interface Code4aApi {

    String CODE4A_ALIPAYINFO = "key.txt";
    String CONTENT_BOARD = "contentBoard";

    Subscription testHttp();

    Subscription getContentBoard(RxSubscriber<ContentBoard> rxSubscriber);

}
