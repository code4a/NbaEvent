package com.code4a.retrofitutil.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jiang on 2018/5/21.
 */

public class NetUtil {

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return 是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return false;
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null) return networkinfo.isAvailable();
        return false;
    }

    /**
     * 判断Wi-Fi是否可用
     *
     * @param context
     * @return 是否连接
     */
    public static boolean isWifiConnected(Context context) {
        if (context == null) return false;
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return false;
        NetworkInfo mWiFiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) return mWiFiNetworkInfo.isAvailable();
        return false;
    }

    /**
     * 判断移动数据是否可用
     *
     * @param context
     * @return 是否是sim卡流量
     */
    public boolean isMobileConnected(Context context) {
        if (context == null) return false;
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return false;
        NetworkInfo mMobileNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetworkInfo != null) return mMobileNetworkInfo.isAvailable();
        return false;
    }
}
