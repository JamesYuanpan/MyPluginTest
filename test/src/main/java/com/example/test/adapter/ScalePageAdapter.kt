package com.example.test.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.test.R
import com.example.test.bean.CardItem
import java.util.Collections.emptyList

class ScalePageAdapter : PagerAdapter() {
    var list: List<CardItem> = emptyList()

    fun setData(data: List<CardItem>) {
        list = data
    }

    override fun getCount(): Int {
        if (list == null) {
            return 0
        }
        if (list.size == 1) {
            return 1
        }
        return Integer.MAX_VALUE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val p = position % list.size
        val data = list[p]
        val view = LayoutInflater.from(container.context).inflate(
            R.layout.card_item,
            container,
            false
        )
        if (view == null) {
            throw RuntimeException("you should set a item layout")
        }
        view.setTag(data)
        if (data != null) {
            setItemData(view, data)
        }
        container.addView(view)
        return view
    }

    private fun setItemData(view: View, detailData: CardItem) {
        val imageView = view.findViewById<ImageView>(R.id.image_view)
        imageView.setImageResource(detailData.description)
        imageView.setBackgroundColor(Color.parseColor(detailData.bgColor))
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}