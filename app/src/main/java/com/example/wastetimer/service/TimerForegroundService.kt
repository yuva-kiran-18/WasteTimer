package com.example.wastetimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.wastetimer.MainActivity
import com.example.wastetimer.repository.TimerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@AndroidEntryPoint
class TimerForegroundService : Service() {

    @Inject
    lateinit var timerRepository: TimerRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var timerJob: Job? = null

    private var sessionStartTime = 0L

    companion object {
        const val CHANNEL_ID = "waste_timer_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

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
            ACTION_START -> startTimer()
            ACTION_STOP -> stopTimer()
        }
        return START_STICKY
    }

    private fun startTimer() {
        if (isRunning) return

        isRunning = true
        sessionStartTime = System.currentTimeMillis()

        startForeground(NOTIFICATION_ID, createNotification("Tracking waste... 0s"))

        timerJob = serviceScope.launch {
            while (isActive) {
                val currentElapsed = System.currentTimeMillis() - sessionStartTime
                _elapsedTime.value = currentElapsed
                updateNotification(currentElapsed)
                delay(1000L)
            }
        }
    }

    private fun stopTimer() {
        if (!isRunning) return

        val sessionEndTime = System.currentTimeMillis()
        val startTime = sessionStartTime

        isRunning = false
        timerJob?.cancel()
        timerJob = null

        _elapsedTime.value = 0L

        serviceScope.launch {
            try {
                timerRepository.saveSession(
                    startTime = startTime,
                    endTime = sessionEndTime
                )
            } finally {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
    }

    private fun createNotification(contentText: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("WasteTimer Active")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(elapsedMillis: Long) {
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        val hours = elapsedMillis / (1000 * 60 * 60)

        val timeString = if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
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

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
        serviceScope.cancel()
    }
}
