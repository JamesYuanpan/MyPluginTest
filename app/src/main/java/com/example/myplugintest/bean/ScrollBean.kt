package com.example.myplugintest.bean

data class ScrollBean(
    val title: String? = "",
    val type: ScrollType = ScrollType.NORMAL,
    val subScrollBean: List<String>? = null,
)

enum class ScrollType {
    NORMAL,
    RECYCLER,
}