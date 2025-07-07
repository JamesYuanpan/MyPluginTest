package com.example.myplugintest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myplugintest.adapter.NestScrollAdapter
import com.example.myplugintest.bean.ScrollBean
import com.example.myplugintest.bean.ScrollType

class NestScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nest_scroll)

        val parentAdapter = NestScrollAdapter(generateData())
        val recycler = findViewById<RecyclerView>(R.id.parent_recycler)

        recycler.apply {
            adapter = parentAdapter
            layoutManager = LinearLayoutManager(context)

            post {
                scrollToPosition(0)
            }
        }
    }

    private fun generateData(): List<ScrollBean> {
        val list = arrayListOf<ScrollBean>()

        val bean1 = ScrollBean(
            type = ScrollType.NORMAL,
            title = "bean1"
        )

        val bean2 = bean1.copy(
            title = "bean2"
        )

        val bean3 = bean1.copy(
            title = "bean3"
        )

        val bean4 = bean1.copy(
            title = "bean4"
        )

        val bean5 = bean1.copy(
            title = "bean5"
        )

        val bean6 = bean1.copy(
            title = "bean6"
        )

        val bean7 = bean1.copy(
            title = "bean7"
        )

        val bean8 = bean1.copy(
            title = "bean8"
        )

        val bean9 = bean1.copy(
            title = "bean9"
        )

        val bean10 = bean1.copy(
            title = "bean10"
        )

        val childList = arrayListOf<String>()

        val child1 = "child1"
        val child2 = "child2"
        val child3 = "child3"
        val child4 = "child4"
        val child5 = "child5"
        val child6 = "child6"
        val child7 = "child7"
        val child8 = "child8"
        val child9 = "child9"
        val child10 = "child10"
        val child11 = "child11"
        val child12 = "child12"
        val child13 = "child13"
        val child14 = "child14"
        val child15 = "child15"
        val child16 = "child16"
        val child17 = "child17"
        val child18 = "child18"
        val child19 = "child19"
        val child20 = "child20"
        val child21 = "child21"
        val child22 = "child22"
        val child23 = "child23"
        val child24 = "child24"
        val child25 = "child25"
        val child26 = "child26"

        childList.add(child1)
        childList.add(child2)
        childList.add(child3)
        childList.add(child4)
        childList.add(child5)
        childList.add(child6)
        childList.add(child7)
        childList.add(child8)
        childList.add(child9)
        childList.add(child10)
        childList.add(child11)
        childList.add(child12)
        childList.add(child13)
        childList.add(child14)
        childList.add(child15)
        childList.add(child16)
        childList.add(child17)
        childList.add(child18)
        childList.add(child19)
        childList.add(child20)
        childList.add(child21)
        childList.add(child22)
        childList.add(child23)
        childList.add(child24)
        childList.add(child25)
        childList.add(child26)

        val bean11 = bean8.copy(
            type = ScrollType.RECYCLER,
            subScrollBean = childList
        )

        list.add(bean1)
        list.add(bean2)
        list.add(bean3)
        list.add(bean4)
        list.add(bean5)
        list.add(bean6)
        list.add(bean7)
        list.add(bean8)
        list.add(bean9)
        list.add(bean10)
        list.add(bean11)

        return list
    }
}