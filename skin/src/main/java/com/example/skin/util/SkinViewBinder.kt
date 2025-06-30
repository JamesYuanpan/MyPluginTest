//package com.example.skin.util
//
//import android.view.View
//
//object SkinViewBinder {
//    private val skinViews = mutableMapOf<Int, SkinView>()
//
//    fun bind(view: View): SkinView {
//        return skinViews.getOrPut(view.id) { SkinView(view) }
//    }
//
//    fun clear(view: View) {
//        skinViews.remove(view.id)
//    }
//}