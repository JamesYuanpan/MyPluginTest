package com.example.component.nestscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ParentRecyclerView2 extends RecyclerView implements NestedScrollingParent3 {
    private final NestedScrollingParentHelper mParentHelper;
    private int mLastY;

    public ParentRecyclerView2(Context context) {
        super(context);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    public ParentRecyclerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    // NestedScrollingParent3 方法实现
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mParentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mParentHelper.onStopNestedScroll(target);
        stopNestedScroll(type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        if (type == ViewCompat.TYPE_TOUCH) {
            final int oldScrollY = getScrollY();
            scrollBy(0, dyUnconsumed);
            final int myConsumed = getScrollY() - oldScrollY;
            consumed[1] += myConsumed;
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (type == ViewCompat.TYPE_TOUCH) {
            final int[] parentConsumed = new int[2];
            if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null, type)) {
                consumed[0] += parentConsumed[0];
                consumed[1] += parentConsumed[1];
            }

            final int remainingDy = dy - consumed[1];
            if (remainingDy != 0) {
                final int oldScrollY = getScrollY();
                scrollBy(0, remainingDy);
                final int myConsumed = getScrollY() - oldScrollY;
                consumed[1] += myConsumed;
            }
        }
    }

    // 触摸事件处理
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final int action = e.getActionMasked();
        final int y = (int) e.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);
                break;
        }

        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        final int action = e.getActionMasked();
        final int y = (int) e.getY();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int dy = y - mLastY;
                mLastY = y;

                if (dispatchNestedPreScroll(0, dy, null, null, ViewCompat.TYPE_TOUCH)) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll(ViewCompat.TYPE_TOUCH);
                break;
        }

        return super.onTouchEvent(e);
    }
}
