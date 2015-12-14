package com.app.CarCia.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ChanZeeBm on 2015/12/1.
 */
public class ClashViewPager extends ViewPager {

    private boolean isEnable = true;

    public ClashViewPager(Context context) {
        super(context);
    }

    public ClashViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isEnable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isEnable && super.onTouchEvent(ev);
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

}
