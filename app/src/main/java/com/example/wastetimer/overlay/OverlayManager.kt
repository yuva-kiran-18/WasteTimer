package com.example.wastetimer.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.wastetimer.ui.overlay.OverlayComponent

class OverlayManager(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var composeView: ComposeView? = null

    fun showOverlay() {
        if (composeView != null) return

        composeView = ComposeView(context).apply {
            setContent {
                OverlayComponent() // Your draggable Compose UI goes here
            }
        }

        // Required to render Compose outside of an Activity
        // Note: LifecycleOwner setup is required here for production via a custom Lifecycle registry.

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 200 // Default Y position
        }

        windowManager.addView(composeView, params)
    }

    fun hideOverlay() {
        composeView?.let {
            windowManager.removeView(it)
            composeView = null
        }
    }
}
