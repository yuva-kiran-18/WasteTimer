package com.example.wastetimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.wastetimer.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerForegroundService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private var timerJob: Job? = null
    
    private var sessionStartTime = 0L

    companion object {
        const val CHANNEL_ID = "waste_timer_channel"
        const val NOTIFICATION_ID = 1
        
        var isRunning = false
            private set

        private val _elapsedTime = MutableStateFlow(0L)
        val elapsedTime: StateFlow<Long> = _elapsedTime
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_START" -> startTimer()
            "ACTION_STOP" -> stopTimer()
        }
        return START_STICKY
    }

    private fun startTimer() {
        if (isRunning) return
        isRunning = true
        sessionStartTime = System.currentTimeMillis()
        
        startForeground(NOTIFICATION_ID, createNotification("Tracking Waste... 0s"))

        timerJob = serviceScope.launch {
            while (isActive) {
                val currentElapsed = System.currentTimeMillis() - sessionStartTime
                _elapsedTime.value = currentElapsed
                updateNotification(currentElapsed)
                delay(1000L) // Update every second
            }
        }
    }

    private fun stopTimer() {
        if (!isRunning) return
        isRunning = false
        timerJob?.cancel()
        
        // TODO: Later we will call the Repository here to save the session to Room
        
        _elapsedTime.value = 0L
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(contentText: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("WasteTimer Active")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Placeholder icon
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(elapsedMillis: Long) {
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        val hours = (elapsedMillis / (1000 * 60 * 60))
        
        val timeString = if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification("Session: $timeString"))
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Timer Notifications",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows the active timer session"
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
