package com.example.component.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.component.fragment.NestedChildFragment

class NestedViewPagerAdapter(
    val list: List<String>,
    fm: FragmentManager,
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return NestedChildFragment.newInstance(list as ArrayList<String>)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return return "Tab " + (position + 1)
    }
}