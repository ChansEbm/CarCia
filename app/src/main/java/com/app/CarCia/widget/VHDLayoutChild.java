package com.app.CarCia.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.app.CarCia.impl.ScrollListener;
import com.app.CarCia.tools.LogTools;

/**
 * Created by ChanZeeBm on 2015/11/30.
 */
public class VHDLayoutChild extends RelativeLayout {
    private float dy;
    private ScrollListener scrollListener;
    public final static int DOWN_DRAG = 0x001;
    public final static int UP_DRAG = 0X002;

    public VHDLayoutChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dy = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (scrollListener != null) {
                    if (dy - event.getY() > 150) {
                        scrollListener.scrollOrientation(UP_DRAG);
                        LogTools.i("upupup");
                    } else {
                        scrollListener.scrollOrientation(DOWN_DRAG);
                        LogTools.i("downdowndown");

                    }
                }
                break;
        }
        return true;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }
}
