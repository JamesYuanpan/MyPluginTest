package com.example.myplugintest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.test_tv)
        textView.text = resources.getString(R.string.jump_second)
    }
}