package com.example.myplugintest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
                RecyclerViewHolder(layoutInflater.inflate(
                    R.layout.scroll_item_recycler,
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
            is RecyclerViewHolder -> {
                val childAdapter = SubScrollAdapter(list[position].subScrollBean ?: emptyList())
                val recyclerViewHolder = holder as RecyclerViewHolder
                recyclerViewHolder.recycler.apply {
                    adapter = childAdapter
                    layoutManager = LinearLayoutManager(context)
                }

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


    class RecyclerViewHolder(itemView: View): ViewHolder(itemView) {
        lateinit var recycler: RecyclerView

        init {
            recycler = itemView.findViewById(R.id.recycler_view)
        }
    }
}