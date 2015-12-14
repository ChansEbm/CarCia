package com.app.CarCia.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.app.CarCia.R;
import com.app.CarCia.impl.DragStateChangedListener;

/**
 * Created by ChanZeeBm on 2015/11/28.
 */
public class VHDLayout extends ViewGroup {
    private View dragView;
    private View topView;
    private View firstView;

    private final static int MIN_DRAG_DISTANCE = 400;
    private ViewDragHelper helper;

    private int topBoundMin;
    private int topBoundMax;

    private boolean isOpen = false;

    private DragStateChangedListener dragStateChangedListener;

    private Point point = new Point();

    public VHDLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        float density = getResources().getDisplayMetrics().density;
        float minVer = density * MIN_DRAG_DISTANCE;

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context
                .WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(point);

        helper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == dragView || child == topView;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return Math.min(Math.max(top, topBoundMin), topBoundMax);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int nowPosition = dragView.getTop();
                int middleLine = getHeight() - dragView.getHeight() + dragView.getHeight() / 2;
                if (yvel == 0.0f) {
                    if (nowPosition < middleLine) {
                        smoothToMax();
                    } else {
                        smoothToMin();
                    }
                } else if (yvel < 0.0f) {
                    smoothToMax();
                } else if (yvel > 0.0f) {
                    smoothToMin();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                helper.captureChildView(dragView, pointerId);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                float offSet = (getHeight() - top + topView.getHeight()) * 1.0f / (changedView
                        .getHeight() - topView
                        .getHeight());
                isOpen = offSet > 0.5f;
                if (dragStateChangedListener != null) {
                    dragStateChangedListener.stateChanged(isOpen);
                }
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return child == dragView ? dragView.getHeight() : 0;
            }
        });

        helper.setMinVelocity(minVer);
        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (helper.shouldInterceptTouchEvent(ev));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        firstView.layout(0, 0, getWidth(), getHeight());
        if (!isOpen) {
            int t = getHeight() - topView.getMeasuredHeight();
            int b = dragView.getMeasuredHeight() + t;
            dragView.layout(0, t, getWidth(), b);
        } else {
            int t = topBoundMin;
            int b = t + dragView.getMeasuredHeight();
            dragView.layout(0, t, getWidth(), b);
        }
        topBoundMin = getHeight()  - dragView.getMeasuredHeight();
        topBoundMax = getHeight() - topView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstView = getChildAt(0);
        dragView = getChildAt(1);
        topView = findViewById(R.id.topView);
    }

    @Override
    public void computeScroll() {
        if (helper.continueSettling(true))
            invalidate();
    }

    private void smoothToMax() {
        helper.smoothSlideViewTo(dragView, dragView.getLeft(), topBoundMin);
        invalidate();
    }

    private void smoothToMin() {
        helper.smoothSlideViewTo(dragView, dragView.getLeft(), topBoundMax);
        invalidate();
    }

    public void trigger() {
        if (isOpen) {
            smoothToMin();
        } else {
            smoothToMax();
        }
    }

    public void setDragStateChangedListener(DragStateChangedListener
                                                    dragStateChangedListener) {
        this.dragStateChangedListener = dragStateChangedListener;
    }
}
