package com.tanhuan.fengsheng.cview;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.activity.WeatherOtherActivity;

public class MyViewPager extends ViewPager {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private int touchSlop;

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = MotionEvent.obtain(ev).getY();
                startX = MotionEvent.obtain(ev).getX();
                Log.e("vp-------Oldy", String.valueOf(ev.getRawY()));
//                break;

            case MotionEvent.ACTION_MOVE:
                endY = ev.getY();
                endX = ev.getX();
                Log.e("vp------newy", String.valueOf(endY));

                float diffX = Math.abs(endX - startX);
                float diffY = Math.abs(endY - startY);

                if (diffY > touchSlop && diffX < diffY) {
                    Log.e("vp---------", "up gesture " );
                    return false;
                }


        }
        return super.onInterceptTouchEvent(ev);
    }
}
