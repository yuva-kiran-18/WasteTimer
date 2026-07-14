package com.example.wastetimer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wastetimer.service.TimerForegroundService
import com.example.wastetimer.ui.screens.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Request permissions for Android 13+ Notifications
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Handle permission grant/deny
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        askForPermissions()

        setContent {
            MaterialTheme { // Using default Material 3 Theme for now
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WasteTimerAppNavHost()
                }
            }
        }
    }

    @Composable
    fun WasteTimerAppNavHost() {
        val navController = rememberNavController()
        
        // State to force recomposition when service starts/stops
        var isServiceRunning by remember { mutableStateOf(TimerForegroundService.isRunning) }

        // Polling state to keep UI button in sync with service
        LaunchedEffect(Unit) {
            while(true) {
                isServiceRunning = TimerForegroundService.isRunning
                delay(500)
            }
        }

        NavHost(navController = navController, startDestination = "dashboard") {
            composable("dashboard") {
                DashboardScreen(
                    onNavigateToStats = { /* navController.navigate("stats") */ },
                    onNavigateToHistory = { /* navController.navigate("history") */ },
                    isServiceRunning = isServiceRunning,
                    onToggleService = {
                        val action = if (isServiceRunning) "ACTION_STOP" else "ACTION_START"
                        val intent = Intent(this@MainActivity, TimerForegroundService::class.java).apply {
                            this.action = action
                        }
                        startService(intent)
                    }
                )
            }
            // Future destinations go here:
            // composable("stats") { StatisticsScreen() }
            // composable("history") { HistoryScreen() }
        }
    }

    private fun askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        // Overlay (SYSTEM_ALERT_WINDOW) requires directing the user to system settings via Intent, 
        // which we will implement when wiring up the OverlayManager.
    }
}
