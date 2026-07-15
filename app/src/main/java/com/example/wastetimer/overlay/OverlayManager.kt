package com.example.wastetimer.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import com.example.wastetimer.ui.overlay.OverlayComponent

class OverlayManager(
    private val context: Context
) {

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var overlayView: ComposeView? = null

    fun showOverlay() {

        if (overlayView != null) return

        if (!Settings.canDrawOverlays(context)) return

        overlayView = ComposeView(context).apply {
            setContent {
                OverlayComponent()
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 200
        }

        windowManager.addView(overlayView, params)
    }

    fun hideOverlay() {

        overlayView?.let {
            windowManager.removeView(it)
            overlayView = null
        }

    }
}
