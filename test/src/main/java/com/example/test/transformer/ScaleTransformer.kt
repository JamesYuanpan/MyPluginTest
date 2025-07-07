package com.example.test.transformer

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.PageTransformer
import com.example.test.R
import kotlin.math.abs
import kotlin.math.floor


class ScaleTransformer : PageTransformer {

    val CENTER_PAGE_SCALE: Float = 1f
    private var offscreenPageLimit = 0
    private var boundViewPager: ViewPager? = null

    //上下边距
    private var verticalInterval = 60

    //右侧边距
    private var endInterval = 50


    constructor(
        boundViewPager: ViewPager,
    ) {
        this.boundViewPager = boundViewPager
        this.offscreenPageLimit = boundViewPager.offscreenPageLimit
    }

    override fun transformPage(view: View, position: Float) {
        val pagerWidth = boundViewPager!!.width
        System.out.println("transformPage tag: " + view.hashCode() + " pos: " + position + " pagerWidth: " + pagerWidth)
        val scaleWidth = pagerWidth * CENTER_PAGE_SCALE
        val widthInterval = (pagerWidth - scaleWidth) / 2

        view.scaleX = CENTER_PAGE_SCALE
        view.scaleY = CENTER_PAGE_SCALE

        //设置间距----------------------------------------------------------------------
        val llRoot = view.findViewById<ViewGroup>(R.id.card_container)
        if (llRoot != null) {
            val layoutParams = llRoot.layoutParams
            if (layoutParams is ConstraintLayout.LayoutParams) {
                layoutParams.marginEnd = ((2 - abs(position.toDouble())) * endInterval).toInt()
                layoutParams.topMargin = (abs(position.toDouble()) * verticalInterval).toInt()
                layoutParams.bottomMargin = (abs(position.toDouble()) * verticalInterval).toInt()
                llRoot.layoutParams = layoutParams
            }
        }
//        if (position >= -1 && position <= 2) {
//            if (this.mAdapter != null) {
//                mAdapter.updateBlurItem(view)
//            }
//        }
        //设置偏移量----------------------------------------------------------------------
        if (position >= 0) {
            view.translationX = -pagerWidth * position
        }
        if (position > -1 && position < 0) {
            view.alpha = (position * position * position + 1)
        } else if (position > offscreenPageLimit - 1) {
            view.alpha = (1 - position + floor(position.toDouble())).toFloat()
        } else {
            view.alpha = 1f
        }
    }
}