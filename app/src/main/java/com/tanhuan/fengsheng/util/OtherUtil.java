package com.tanhuan.fengsheng.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

public class OtherUtil {
    //日期转换为日
    public static String toDay(String date) {
        String[] dateArray = date.split("-");
        return dateArray[dateArray.length - 1];
    }

}
