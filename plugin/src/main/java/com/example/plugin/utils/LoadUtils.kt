package com.example.plugin.utils

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources

object LoadUtils {
    private var mResources: Resources? = null

    /**
     * 只有mResource为空时才创建
     */
    fun getResource(context: Context): Resources {
        if (mResources == null) {
            mResources = loadAsset(context)
        }
        return mResources!!
    }

    private fun loadAsset(context: Context): Resources? {
        try {
            // 创建AssetManager对象
            val assetManager = AssetManager::class.java.newInstance()
            // 执行addAssetPath方法，添加资源加载路径
            val assetManagerMethod = assetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            assetManagerMethod.isAccessible = true
            assetManagerMethod.invoke(assetManager, "sdcard/my-plugin-debug.apk")

            // 创建Resource
            val resources = Resources(
                assetManager,
                context.resources.displayMetrics,
                context.resources.configuration
            )
            println("yp====  assetManager = " + assetManager)
            println("yp====  resources = " + resources)

            return resources
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}