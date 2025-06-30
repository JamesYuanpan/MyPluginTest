package com.example.test.util

import android.view.View
import android.widget.TextView
import com.example.test.manager.SkinManager

sealed class SkinAttribute {
    abstract fun apply(view: View, skinResource: SkinManager.SkinResource?)

    data class BackgroundColorAttribute(
        val lightColor: Int,
        val darkColor: Int,
        val customColorResName: String?
    ) : SkinAttribute() {
        override fun apply(view: View, skinResource: SkinManager.SkinResource?) {
            when (SkinManager.getInstance().currentTheme.value) {
                SkinManager.SkinTheme.LIGHT -> view.setBackgroundColor(lightColor)
                SkinManager.SkinTheme.DARK -> view.setBackgroundColor(darkColor)
                SkinManager.SkinTheme.CUSTOM -> {
                    customColorResName?.let { resName ->
                        skinResource?.let {
                            view.setBackgroundColor(it.getColor(resName))
                        }
                    }
                }
                null -> view.setBackgroundColor(lightColor)
            }
        }
    }

    data class TextColorAttribute(
        val lightColor: Int,
        val darkColor: Int,
        val customColorResName: String?
    ) : SkinAttribute() {
        override fun apply(view: View, skinResource: SkinManager.SkinResource?) {
            if (view !is TextView) return

            when (SkinManager.getInstance().currentTheme.value) {
                SkinManager.SkinTheme.LIGHT -> view.setTextColor(lightColor)
                SkinManager.SkinTheme.DARK -> view.setTextColor(darkColor)
                SkinManager.SkinTheme.CUSTOM -> {
                    customColorResName?.let { resName ->
                        skinResource?.let {
                            view.setTextColor(it.getColor(resName))
                        }
                    }
                }
                null -> view.setTextColor(lightColor)
            }
        }
    }

    // 可以继续添加其他属性类型...
}