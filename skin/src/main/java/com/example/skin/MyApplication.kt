//package com.example.skin

//import android.app.Application
//import android.view.LayoutInflater
//import com.example.skin.manager.SkinManager
//import com.example.skin.util.SkinLayoutInflaterFactory

//class MyApplication : Application() {
//    override fun onCreate() {
//        super.onCreate()
//
//        LayoutInflater.from(this).setFactory2(
//            SkinLayoutInflaterFactory(LayoutInflater.from(this).factory2)
//        )
//        SkinManager.init(this)
//    }
//}