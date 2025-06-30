package com.example.test.extend

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.test.util.SkinAttribute
import com.example.test.util.SkinViewBinder

fun View.applySkinAttributes(vararg attributes: SkinAttribute) {
    SkinViewBinder.bind(this).apply {
        attributes.forEach { addAttribute(it) }
    }
}

fun View.clearSkinAttributes() {
    SkinViewBinder.clear(this)
}

// 常用属性的便捷扩展
fun View.skinBackground(
    lightColor: Int,
    darkColor: Int,
    customColorResName: String? = null
) {
    val context = context
    applySkinAttributes(
        SkinAttribute.BackgroundColorAttribute(
            lightColor = ContextCompat.getColor(context, lightColor),
            darkColor = ContextCompat.getColor(context, darkColor),
            customColorResName = customColorResName
        )
    )
}

fun TextView.skinTextColor(
    lightColor: Int,
    darkColor: Int,
    customColorResName: String? = null
) {
    val context = context
    applySkinAttributes(
        SkinAttribute.TextColorAttribute(
            lightColor = ContextCompat.getColor(context, lightColor),
            darkColor = ContextCompat.getColor(context, darkColor),
            customColorResName = customColorResName
        )
    )
}