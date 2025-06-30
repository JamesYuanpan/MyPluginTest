package com.example.test.util

import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.example.test.manager.SkinManager

class SkinView(private val view: View) : LifecycleObserver {
    private val attributes = mutableListOf<SkinAttribute>()
    private val skinResourceObserver = Observer<SkinManager.SkinTheme> {
        applySkin()
    }

    init {
        if (view.context is LifecycleOwner) {
            (view.context as LifecycleOwner).lifecycle.addObserver(this)
        }

        SkinManager.getInstance().currentTheme.observe(
            getLifecycleOwner(),
            skinResourceObserver
        )
    }

    fun addAttribute(attribute: SkinAttribute) {
        attributes.add(attribute)
        attribute.apply(view, SkinManager.getInstance().getCurrentSkinResource())
    }

    private fun applySkin() {
        val skinResource = SkinManager.getInstance().getCurrentSkinResource()
        attributes.forEach { it.apply(view, skinResource) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        SkinManager.getInstance().currentTheme.removeObserver(skinResourceObserver)
    }

    private fun getLifecycleOwner(): LifecycleOwner {
        return when {
            view.context is LifecycleOwner -> view.context as LifecycleOwner
            view.context is ContextWrapper &&
                    (view.context as ContextWrapper).baseContext is LifecycleOwner ->
                (view.context as ContextWrapper).baseContext as LifecycleOwner
            else -> throw IllegalStateException("View's context must be a LifecycleOwner")
        }
    }
}