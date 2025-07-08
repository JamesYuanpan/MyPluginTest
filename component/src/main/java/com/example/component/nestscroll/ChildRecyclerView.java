package com.example.component.nestscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ChildRecyclerView extends RecyclerView {
    private final NestedScrollingChildHelper mChildHelper;
    private int mLastY;

    public ChildRecyclerView(Context context) {
        super(context);
        mChildHelper = new NestedScrollingChildHelper(this);
    }

    public ChildRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mChildHelper = new NestedScrollingChildHelper(this);
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getParentRecyclerView(this);
    }

    private void getParentRecyclerView(ViewGroup viewGroup) {
        ViewParent parent = viewGroup.getParent();
        System.out.println("yp====  getParentRecyclerView ... " + parent);
        while(parent != null) {
            System.out.println("yp====  getParentRecyclerView " + parent);
            if (parent instanceof ParentRecyclerView) {
                ((ParentRecyclerView) parent).setChildRecyclerView(this);
                return;
            }
            parent = parent.getParent();
        }
    }
}