package com.example.test.util

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.test.R
import com.example.test.extend.skinBackground
import com.example.test.extend.skinTextColor

class SkinLayoutInflaterFactory(
    private val originalFactory: LayoutInflater.Factory2?
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val view = originalFactory?.onCreateView(parent, name, context, attrs)
            ?: createView(name, context, attrs)

        view?.let { parseSkinAttributes(it, context, attrs) }
        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = originalFactory?.onCreateView(name, context, attrs)
            ?: createView(name, context, attrs)

        view?.let { parseSkinAttributes(it, context, attrs) }
        return view
    }

    private fun parseSkinAttributes(view: View, context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinAttributes)

        try {
            // 解析背景属性
            if (typedArray.hasValue(R.styleable.SkinAttributes_skinBackgroundColorLight)) {
                val lightColor = typedArray.getColor(
                    R.styleable.SkinAttributes_skinBackgroundColorLight,
                    Color.TRANSPARENT
                )
                val darkColor = typedArray.getColor(
                    R.styleable.SkinAttributes_skinBackgroundColorDark,
                    Color.TRANSPARENT
                )
                val customResName = typedArray.getString(
                    R.styleable.SkinAttributes_skinBackgroundColorCustom
                )

                view.skinBackground(
                    lightColor = lightColor,
                    darkColor = darkColor,
                    customColorResName = customResName
                )
            }

            // 解析文本颜色属性
            if (view is TextView &&
                typedArray.hasValue(R.styleable.SkinAttributes_skinTextColorLight)) {
                val lightColor = typedArray.getColor(
                    R.styleable.SkinAttributes_skinTextColorLight,
                    Color.BLACK
                )
                val darkColor = typedArray.getColor(
                    R.styleable.SkinAttributes_skinTextColorDark,
                    Color.WHITE
                )
                val customResName = typedArray.getString(
                    R.styleable.SkinAttributes_skinTextColorCustom
                )

                view.skinTextColor(
                    lightColor = lightColor,
                    darkColor = darkColor,
                    customColorResName = customResName
                )
            }
        } finally {
            typedArray.recycle()
        }
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        return try {
            if (-1 == name.indexOf('.')) {
                when (name) {
                    "View" -> LayoutInflater.from(context).createView(name, "android.view.", attrs)
                    else -> LayoutInflater.from(context).createView(name, "android.widget.", attrs)
                }
            } else {
                LayoutInflater.from(context).createView(name, null, attrs)
            }
        } catch (e: Exception) {
            null
        }
    }
}