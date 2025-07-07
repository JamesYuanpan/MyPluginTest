package com.example.myplugintest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myplugintest.utils.HookUtil
import com.example.myplugintest.utils.LoadApkUtil
import com.example.test.TestActivity

//import com.example.skin.SkinMainActivity

class PluginMainTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val arr = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED) {
            println("yp====  来申请权限 ")
            ActivityCompat.requestPermissions(this, arr, 10)
        }

        findViewById<Button>(R.id.load_plugin).setOnClickListener {
            loadPlugin()
        }

        findViewById<Button>(R.id.plugin_test_btn).setOnClickListener {
            loadApk()
        }


        findViewById<Button>(R.id.jump_second_btn).setOnClickListener {
            val intent = Intent(this@PluginMainTestActivity,
//                SkinMainActivity::class.java)
                TestActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.component_page).setOnClickListener {
            val intent = Intent(this@PluginMainTestActivity,
//                ComponentActivity::class.java)
                SecondActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.nest_scroll).setOnClickListener {
            val intent = Intent(this@PluginMainTestActivity,
                NestScrollActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 打开插件页面
     */
    private fun loadApk() {
        try {
            // 调用插件类
            val cls = Class.forName("com.example.plugin.test.Test")
            val method = cls.getDeclaredMethod("test", Context::class.java)
            method.invoke(cls.newInstance(), this)

            // 跳转插件 Activity
            val intent = Intent()
            intent.component = ComponentName(
                "com.example.plugin",
                "com.example.plugin.test.PluginTestActivity"
            )

            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var isLoaded = false

    fun loadPlugin() {
        if (!isLoaded) {
            isLoaded = true
            // 加载class文件
            LoadApkUtil.loadApk(this)

            // hook
            HookUtil.hookAms()
            HookUtil.hookHandler()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            println("yp==== request permission success")
        } else {
            println("yp==== request permission failed")
        }
    }
}