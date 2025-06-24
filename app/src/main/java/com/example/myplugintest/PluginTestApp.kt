package com.example.myplugintest

import android.app.Application
import android.content.res.Resources
import com.example.myplugintest.utils.LoadApkUtil

class PluginTestApp : Application() {
    private var mResources: Resources? = null

    override fun onCreate() {
        super.onCreate()
        if (mResources == null) {
            mResources = LoadApkUtil.loadAsset(this)
        }
    }


    override fun getResources(): Resources {
        if (mResources == null) {
            return super.getResources()
        }
        return mResources!!
    }
}