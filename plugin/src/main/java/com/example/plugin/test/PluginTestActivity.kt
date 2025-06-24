package com.example.plugin.test

import android.os.Bundle
import com.example.plugin.R
import com.example.plugin.common.BaseActivity

class PluginTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_plugin_test, null, false)
//        setContentView(view)

        setContentView(R.layout.activity_plugin_test)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}