package com.example.component.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.component.fragment.NestedChildFragment
import com.example.component.nested_scrolling_layout.NestedScrollingParent2LayoutImpl3

class NestedViewPagerAdapter(
    val list: List<String>,
    fm: FragmentManager,
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var nestedScrollingParent2Layout: NestedScrollingParent2LayoutImpl3? = null

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return NestedChildFragment.newInstance(list as ArrayList<String>).apply {
            mNestedScrollingParent2Layout = nestedScrollingParent2Layout
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return return "Tab " + (position + 1)
    }
}