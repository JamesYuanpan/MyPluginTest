package com.example.component.nestscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView

class ParentRecyclerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0,
) : RecyclerView(context, attributeSet, def),
    NestedScrollingParent3
{
    lateinit var mParentHelper: NestedScrollingParentHelper

    var childRecyclerView: RecyclerView? = null

    init {
        mParentHelper = NestedScrollingParentHelper(this)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        // 只处理垂直滑动
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mParentHelper.onStopNestedScroll(target, type)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        getChildRecyclerView()
    }

    open fun getChildRecyclerView() {
        println("yp==== getChildRecyclerView ... " + childCount)
         for (i in 0 until childCount) {
             val childView = getChildAt(i)
             println("yp==== getChildRecyclerView  childView  ... " + childView)
             if (childView is RecyclerView) {
                 childRecyclerView = childView
             }
         }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        // 处理未消耗的滑动距离
        val oldScrollY = scrollY
        scrollBy(0, dyUnconsumed)
        val dy = scrollY - oldScrollY
        dispatchNestedScroll(
            0,
            dy,
            0,
            dyUnconsumed - dy,
            null,
            type
        )
    }

    /**
     * 嵌套滑动的子控件在滑动之后，判断父控件是否继续处理（也就是父消耗一定距离后，子再消耗，最后判断父消耗不）
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子控件滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子控件滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子控件未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子控件未滑动的距离(未消耗的距离)
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        // 处理未消耗的滑动距离
        val oldScrollY = scrollY
        scrollBy(0, dyUnconsumed)
        val dy = scrollY - oldScrollY
        dispatchNestedScroll(
            0,
            dy,
            0,
            dyUnconsumed - dy,
            null,
            type
        )
    }

    /**
     * 在嵌套滑动的子控件未滑动之前，判断父控件是否优先与子控件处理(也就是父控件可以先消耗，然后给子控件消耗）
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子控件想要变化的距离 dx<0 向右滑动 dx>0 向左滑动
     * @param dy       垂直方向嵌套滑动的子控件想要变化的距离 dy<0 向下滑动 dy>0 向上滑动
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子控件当前父控件消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子控件做出相应的调整
     */
    override fun onNestedPreScroll(
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // 在子视图处理滑动之前，父视图可以先消耗部分滑动距离
        println("yp====  scrollY = " + scrollY)
        println("yp====  height = " + height)
        println("yp====  canScrollVertically(1) = " + canScrollVertically(1))
        println("yp====  canScrollVertically(-1) = " + canScrollVertically(-1))


//        if (dy > 0 && scrollY < height) {
//            // 向上滑动并且外层recyclerview还未滑出屏幕
//            val consumedY = Math.min(dy, height - scrollY)
//            scrollBy(0, consumedY)
//            consumed[1] = consumedY
//        }

        if (childRecyclerView == null) {
            return
        }

        val childTop = childRecyclerView?.top ?: 0

        handleParentRecyclerViewScroll(childTop, dy, consumed)
    }

    /**
     * 滑动外层RecyclerView时，的处理
     *
     * @param childTop tab到屏幕顶部的距离，是0就代表到顶了
     * @param dy          目标滑动距离， dy>0 代表向上滑
     * @param consumed
     */
    private fun handleParentRecyclerViewScroll(childTop: Int, dy: Int, consumed: IntArray) {
        if (childTop != 0) {
            if (dy > 0) {
                // 向上滑
                if (childTop > dy) {
                    //tab的top>想要滑动的dy,就让外部RecyclerView自行处理
                } else {
                    //tab的top<=想要滑动的dy,先滑外部RecyclerView，childTop，刚好到顶；剩下的就滑内层了。
                    consumed[1] = dy
                    scrollBy(0, childTop)
                    childRecyclerView?.scrollBy(0, dy - childTop)
                }
            } else {
                //向下滑，就让外部RecyclerView自行处理
            }
        } else {
            //tab上边到顶了
            if (dy > 0) {
                //向上，内层直接消费掉
                childRecyclerView?.scrollBy(0, dy)
                consumed[1] = dy
            } else {
                val childScrollY = childRecyclerView?.computeVerticalScrollOffset() ?: 0
                if (childScrollY > Math.abs(dy)) {
                    //内层已滚动的距离，大于想要滚动的距离，内层直接消费掉
                    childRecyclerView?.scrollBy(0, dy)
                    consumed[1] = dy
                } else {
                    //内层已滚动的距离，小于想要滚动的距离，那么内层消费一部分，到顶后，剩的还给外层自行滑动
                    childRecyclerView?.scrollBy(0, childScrollY)
                    consumed[1] = -childScrollY
                }
            }
        }

    }

    /**
     * 当父控件不拦截该fling,那么子控件会将fling传入父控件
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向上的速度 velocityX > 0  向左滑动，反之向右滑动
     * @param velocityY 竖直方向上的速度 velocityY > 0  向上滑动，反之向下滑动
     * @param consumed  子控件是否可以消耗该fling，也可以说是子控件是否消耗掉了该fling
     * @return 父控件是否消耗了该fling
     */
    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean,
    ): Boolean {
        // 在子视图处理快速滑动之后，父视图可以处理剩余的滑动事件
        if (!consumed && velocityY < 0 && scrollY > 0) {
            fling(0, velocityY.toInt())
            return true
        }
        return false
    }

    /**
     * 当子控件产生fling滑动时，判断父控件是否处拦截fling，如果父控件处理了fling，那子控件就没有办法处理fling了。
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向上的速度 velocityX > 0  向左滑动，反之向右滑动
     * @param velocityY 竖直方向上的速度 velocityY > 0  向上滑动，反之向下滑动
     * @return 父控件是否拦截该fling
     */
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        // 在子视图处理快速滑动之前，父视图可以选择是否消耗快速滑动事件
        if (velocityY > 0 && scrollY < height) {
            fling(0, velocityY.toInt())
            return true
        }
        return false
    }

    private var mLast: Float = 0f

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val currentY = e.y

        when(e.action) {
            MotionEvent.ACTION_DOWN -> {
                mLast = currentY
            }

            MotionEvent.ACTION_MOVE -> {
//                val deltaY = currentY - mLast
//                mLast = currentY
//                if (deltaY > 0) {
//                    println("yp==== onInterceptTouchEvent 111 ....... ")
//                    // 向下滑动
//                    if (canScrollVertically(1)) {
//                        println("yp==== onInterceptTouchEvent 222 ....... ")
//                        // 外层 RecyclerView 可以向下滑动，拦截事件
//                        return true
//                    } else if (childRecyclerView != null && childRecyclerView?.canScrollVertically(1) == true) {
//                        println("yp==== onInterceptTouchEvent 333 ....... ")
//                        return false
//                    }
//                } else if (deltaY < 0) {
//                    println("yp==== onInterceptTouchEvent 444 ....... childRecyclerView = " + childRecyclerView)
//                    // 向上滑动
//                    if (childRecyclerView != null && childRecyclerView?.canScrollVertically(-1) == true) {
//                        // 内层 RecyclerView 可以向上滑动，不拦截事件
//                        println("yp==== onInterceptTouchEvent 555 ....... ")
//                        return false;
//                    } else if (canScrollVertically(-1)) {
//                        // 内层 RecyclerView 不能滑动，外层 RecyclerView 可以滑动，拦截事件
//                        println("yp==== onInterceptTouchEvent 666 ....... ")
//                        return true;
//                    }
//                }

                return false
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}