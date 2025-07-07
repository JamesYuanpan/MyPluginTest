package com.example.myplugintest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.view.StackView

class ComponentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component)

        val stackView = findViewById<StackView>(R.id.stackView)

        stackView.initComponent()
    }
}