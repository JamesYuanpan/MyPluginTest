package com.example.test.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.test.R
import com.example.test.adapter.ScalePageAdapter
import com.example.test.bean.CardItem
import com.example.test.transformer.ScaleTransformer

class StackView(
    context: Context,
    attributeSet: AttributeSet? = null,
) : FrameLayout(context, attributeSet) {

    val layout = LayoutInflater.from(context).inflate(
        R.layout.stack_view_layout,
        this
    )

    fun initComponent() {
        val viewPager = layout.findViewById<ViewPager>(R.id.view_pager)

        val items = listOf(
            CardItem("1", R.drawable.card1, "#ff0000"),
            CardItem("2", R.drawable.card2, "#00ff00"),
            CardItem("3", R.drawable.card3, "#0000ff"),
            CardItem("4", R.drawable.card4, "#ffff00"),
        )
        val scaleAdapter = ScalePageAdapter()
        scaleAdapter.setData(items)

        viewPager.adapter = scaleAdapter
        viewPager.offscreenPageLimit = 4

        viewPager.setPageTransformer(true, ScaleTransformer(
            viewPager
        ))

        viewPager.addOnPageChangeListener(object : OnPageChangeListener{
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
    }
}