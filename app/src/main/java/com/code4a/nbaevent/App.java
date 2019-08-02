package com.code4a.nbaevent;

import android.app.Application;

import com.code4a.simpleapi.SimpleManager;

/**
 * Created by code4a on 2017/7/11.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SimpleManager.init(this);
    }
}
