//package com.example.skin
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import com.example.skin.extend.skinBackground
//import com.example.skin.extend.skinTextColor
//import com.example.skin.manager.SkinManager
//
//class SkinMainActivity : BaseSkinActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_skin_main)
//
//        // 绑定皮肤属性
//        findViewById<TextView>(R.id.text_view).apply {
//            skinTextColor(
//                lightColor = R.color.text_light,
//                darkColor = R.color.text_dark,
//                customColorResName = "text_color_custom"
//            )
//        }
//
//        findViewById<View>(R.id.background_view).apply {
//            skinBackground(
//                lightColor = R.color.background_light,
//                darkColor = R.color.background_dark,
//                customColorResName = "background_custom"
//            )
//        }
//
//        // 切换主题按钮
//        findViewById<Button>(R.id.btn_light).setOnClickListener {
//            switchTheme(SkinManager.SkinTheme.LIGHT)
//        }
//
//        findViewById<Button>(R.id.btn_dark).setOnClickListener {
//            switchTheme(SkinManager.SkinTheme.DARK)
//        }
//
//        findViewById<Button>(R.id.btn_custom).setOnClickListener {
//            // 假设皮肤包已下载到指定路径
////            val skinPath = "${getBaseContext().getCacheDir()}/skin-debug.apk"
//            val skinPath = "${getBaseContext().getCacheDir()}/skin-debug-activity.apk"
//            if (loadSkinPackage(skinPath)) {
//                switchTheme(SkinManager.SkinTheme.CUSTOM)
//            }
//        }
//    }
//}