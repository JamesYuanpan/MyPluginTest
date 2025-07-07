package com.example.myplugintest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myplugintest.R

class SubScrollAdapter(
    val list: List<String>
) : Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.scroll_sub_item,
            parent,
            false
        )
        return TextViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as? TextViewHolder)?.title?.text = list[position]
    }


    class TextViewHolder(val itemView: View) : ViewHolder(itemView) {
        lateinit var title: TextView

        init {
            title = itemView.findViewById(R.id.sub_title)
        }
    }
}