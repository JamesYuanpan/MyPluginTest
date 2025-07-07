package com.example.myplugintest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.component.nestscroll.NestedAndScrollView
import com.example.myplugintest.R
import com.example.myplugintest.bean.ScrollBean
import com.example.myplugintest.bean.ScrollType

class NestScrollAdapter(
    val list: List<ScrollBean>
) : Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            ScrollType.RECYCLER.ordinal -> {
                NestedViewHolder(layoutInflater.inflate(
                    R.layout.scroll_item_nested,
                    parent,
                    false
                ))
            }

            else -> {
                NormalViewHolder(layoutInflater.inflate(
                    R.layout.scroll_item_normal,
                    parent,
                    false
                ))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is NestedViewHolder -> {
                val nestedViewHolder = holder as NestedViewHolder
                nestedViewHolder.nestedView.setData(list[position].subScrollBean ?: emptyList())
            }

            else -> {
                val normalViewHolder = (holder as NormalViewHolder)

                normalViewHolder.title.text = list[position].title
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type.ordinal
    }


    class NormalViewHolder(itemView: View) : ViewHolder(itemView) {
        lateinit var title: TextView

        init {
            title = itemView.findViewById(R.id.title_normal)
        }
    }


    class NestedViewHolder(itemView: View): ViewHolder(itemView) {
        lateinit var nestedView: NestedAndScrollView

        init {
            nestedView = itemView.findViewById(R.id.nest_view)
        }
    }
}