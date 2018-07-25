package com.tanhuan.fengsheng;

import android.app.Application;
import android.widget.Toast;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void ToastUtil(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
    }

}
