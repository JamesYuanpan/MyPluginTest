package com.example.plugin.common

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.example.plugin.utils.LoadUtils

open class BaseActivity : AppCompatActivity() {
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 获取自己创建的resource
        val resource = LoadUtils.getResource(application)
        // 创建自己的context
        mContext = ContextThemeWrapper(baseContext, 0)
        // 把原先context中的resource替换为自己写的resource
        val clazz = mContext::class.java
        val mResourceField = clazz.getDeclaredField("mResources")
        mResourceField.isAccessible = true
        mResourceField.set(mContext, resource)

    }
}