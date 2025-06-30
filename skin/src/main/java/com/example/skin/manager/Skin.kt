//package com.example.skin.manager
//
//import android.content.Context
//import android.content.pm.PackageManager
//import android.content.res.AssetManager
//import android.content.res.Resources
//import android.graphics.Color
//import android.graphics.drawable.Drawable
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class SkinManager private constructor(context: Context) {
//    // 当前皮肤主题的LiveData
//    private val _currentTheme = MutableLiveData<SkinTheme>()
//    val currentTheme: LiveData<SkinTheme> = _currentTheme
//
//    // 皮肤资源缓存
//    private val skinResources = mutableMapOf<String, SkinResource>()
//
//    // 应用上下文
//    private val appContext = context.applicationContext
//
//    companion object {
//        @Volatile
//        private var instance: SkinManager? = null
//
//        fun init(context: Context): SkinManager {
//            return instance ?: synchronized(this) {
//                instance ?: SkinManager(context).also { instance = it }
//            }
//        }
//
//        fun getInstance(): SkinManager {
//            return instance ?: throw IllegalStateException("SkinManager must be initialized first")
//        }
//    }
//
//    // 皮肤主题枚举
//    enum class SkinTheme {
//        LIGHT, DARK, CUSTOM
//    }
//
//    // 加载自定义皮肤包
//    fun loadSkinPackage(skinPath: String): Boolean {
//        return try {
//            val packageInfo = appContext.packageManager.getPackageArchiveInfo(
//                skinPath,
//                PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES
//            ) ?: return false
//
//            val assetManager = AssetManager::class.java.newInstance()
//            val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
//            addAssetPath.invoke(assetManager, skinPath)
//
//            val resources = Resources(
//                assetManager,
//                appContext.resources.displayMetrics,
//                appContext.resources.configuration
//            )
//
//            skinResources[packageInfo.packageName] = SkinResource(
//                resources = resources,
//                packageName = packageInfo.packageName
//            )
//
//            true
//        } catch (e: Exception) {
//            e.printStackTrace()
//            false
//        }
//    }
//
//    // 切换主题
//    fun switchTheme(theme: SkinTheme) {
//        _currentTheme.value = theme
//    }
//
//    // 获取当前皮肤资源
//    fun getCurrentSkinResource(): SkinResource? {
//        return when (_currentTheme.value) {
//            SkinTheme.CUSTOM -> skinResources.values.firstOrNull()
//            else -> null
//        }
//    }
//
//    // 皮肤资源封装类
//    data class SkinResource(
//        val resources: Resources,
//        val packageName: String
//    ) {
//        fun getColor(resName: String): Int {
//            val resId = resources.getIdentifier(resName, "color", packageName)
//            return if (resId != 0) resources.getColor(resId) else Color.TRANSPARENT
//        }
//
//        fun getDrawable(resName: String): Drawable? {
//            val resId = resources.getIdentifier(resName, "drawable", packageName)
//            return if (resId != 0) resources.getDrawable(resId) else null
//        }
//    }
//}