package com.example.plugin.test

import android.content.Context
import android.widget.Toast

class Test {

    fun test(context: Context) {
        System.out.println("调用成功了")
        Toast.makeText(context, "调用成功了", Toast.LENGTH_SHORT).show()
    }
}