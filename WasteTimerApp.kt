package com.example.wastetimer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WasteTimerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Future initialization (e.g., Notification Channels) will go here
    }
}
