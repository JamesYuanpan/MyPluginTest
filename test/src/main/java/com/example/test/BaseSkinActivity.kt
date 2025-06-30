package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.manager.SkinManager

abstract class BaseSkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        SkinManager.init(this)
        super.onCreate(savedInstanceState)
    }

    fun switchTheme(theme: SkinManager.SkinTheme) {
        SkinManager.getInstance().switchTheme(theme)
    }

    fun loadSkinPackage(skinPath: String): Boolean {
        return SkinManager.getInstance().loadSkinPackage(skinPath)
    }
}