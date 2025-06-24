package com.example.myplugintest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Environment
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.reflect.Array
import kotlin.jvm.internal.Intrinsics.Kotlin

object LoadApkUtil {

    /**
     * 加载其他apk
     */
    @SuppressLint("DiscouragedPrivateApi")
    fun loadApk(context: Context) {
        try {
            // 获取pathList
            val systemClassLoader = Class.forName("dalvik.system.BaseDexClassLoader")
            val pathListField = systemClassLoader.getDeclaredField("pathList")
            pathListField.isAccessible = true

            // 获取dex element数组
            val dexPathListClass = Class.forName("dalvik.system.DexPathList")
            val dexElementField = dexPathListClass.getDeclaredField("dexElements")
            dexElementField.isAccessible = true

            // 获取宿主的Elements
            val hostClassLoader = context.classLoader
            val hostPathList = pathListField.get(hostClassLoader)
            val hostElements = dexElementField.get(hostPathList) as kotlin.Array<Any>

            // 获取插件中的Elements
            val pluginClassLoader = DexClassLoader(
                "sdcard/my-plugin-debug.apk",
//                "sdcard/plugin-debug.apk",
                context.cacheDir.absolutePath,
                null,
                context.classLoader
            )

            println("yp==== file = " + Environment.getExternalStorageDirectory().absolutePath)

            println("yp====   pluginClassLoader  = " + pluginClassLoader)
            val pluginPathList = pathListField.get(pluginClassLoader)
            val pluginElements = dexElementField.get(pluginPathList) as kotlin.Array<Any>

            println("yp==== pluginPathList = 00 " + pluginPathList)
            println("yp==== pluginElements = 00 " + pluginElements)

            // 创建新数组---用来存放合并后的Elements
            val newElements = Array.newInstance(
                pluginElements.javaClass.componentType,
                hostElements.size + pluginElements.size
            ) as kotlin.Array<Any>

            // 合并宿主和插件的elements
            System.arraycopy(hostElements, 0, newElements, 0, hostElements.size)
            System.arraycopy(pluginElements, 0, newElements, hostElements.size, pluginElements.size)

            // 重新赋值
            dexElementField.set(hostPathList, newElements)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadAsset(context: Context): Resources? {
        try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPathMethod = assetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPathMethod.isAccessible = true
            addAssetPathMethod.invoke(assetManager, "sdcard/my-plugin-debug.apk")

            return Resources(
                assetManager,
                context.resources.displayMetrics,
                context.resources.configuration
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}