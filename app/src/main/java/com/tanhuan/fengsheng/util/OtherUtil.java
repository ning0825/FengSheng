package com.tanhuan.fengsheng.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;

public class OtherUtil {
    //日期转换为日
    public static String toDay(String date) {
        String[] dateArray = date.split("-");
        return dateArray[dateArray.length - 1];
    }

    //判断是否有网络连接
    public static boolean hasNetWork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    //finish activity
    public static void finish(Activity activity) {
        activity.finish();
    }

    //初始化，获取数据

}
