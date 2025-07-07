package com.example.component.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.component.R
import com.example.component.adapter.SubScrollAdapter

class NestedChildFragment : Fragment() {
    companion object {
        const val KEY_STRING_LIST = "KEY_STRING_LIST"

        fun newInstance(list: ArrayList<String>): NestedChildFragment {

            val fragment = NestedChildFragment()
            fragment.arguments = Bundle().apply {
                putStringArrayList(KEY_STRING_LIST, list)
            }
            return fragment
        }
    }

    var rootView: View? = null

    var mList: List<String>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(
                R.layout.scroll_item_recycler,
                container,
                false
            )
        }
        mList = arguments?.getStringArrayList(KEY_STRING_LIST)
        setData(mList ?: emptyList())
        return rootView
    }

    private fun setData(list: List<String>) {
        val childAdapter = SubScrollAdapter(
            list
        )
        val recycler = rootView?.findViewById<RecyclerView>(R.id.recycler_view)
        recycler?.apply {
            adapter = childAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}