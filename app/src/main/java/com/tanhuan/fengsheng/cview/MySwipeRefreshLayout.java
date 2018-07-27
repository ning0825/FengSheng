package com.tanhuan.fengsheng.cview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.tanhuan.fengsheng.MainActivity;
import com.tanhuan.fengsheng.activity.WeatherOtherActivity;
import com.tanhuan.fengsheng.entity.WeatherOther;

import java.util.jar.Attributes;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private int touchSlop;

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = MotionEvent.obtain(ev).getX();
                startY = MotionEvent.obtain(ev).getY();
                break;

            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();

                float diff = Math.abs(endX - startX);
                boolean up = endY < startY;

                if ((diff > touchSlop) || up) {
                    return false;
                }
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

}
