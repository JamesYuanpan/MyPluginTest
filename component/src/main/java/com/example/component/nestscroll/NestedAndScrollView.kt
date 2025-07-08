package com.example.component.nestscroll

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.component.R
import com.example.component.adapter.NestedViewPagerAdapter
import com.example.component.nested_scrolling_layout.NestedScrollingParent2LayoutImpl3
import com.google.android.material.tabs.TabLayout

class NestedAndScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0,
) : FrameLayout(context, attributeSet, def) {
    val layout = LayoutInflater.from(context).inflate(
        R.layout.nested_scroll_layout,
        this,
        true
    )

    fun setData(list: List<String>, scrollingParent2Layout: NestedScrollingParent2LayoutImpl3? = null) {
        println("yp====  nestedAndScrollView setData ... ")
        val viewpager = layout.findViewById<ViewPager>(R.id.viewpager)
        val tabLayout = layout.findViewById<TabLayout>(R.id.tab_layout)

        viewpager.adapter = NestedViewPagerAdapter(list, (context as FragmentActivity).supportFragmentManager).apply {
            nestedScrollingParent2Layout = scrollingParent2Layout
        }

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        tabLayout.setupWithViewPager(viewpager)
    }
}