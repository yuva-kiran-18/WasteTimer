package com.example.wastetimer.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wastetimer.ui.dashboard.components.DashboardCard
import com.example.wastetimer.ui.dashboard.components.ResetDialog
import com.example.wastetimer.ui.dashboard.components.SessionCard
import com.example.wastetimer.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onToggleService: () -> Unit,
    isServiceRunning: Boolean,
    onNavigateToHistory: () -> Unit,
    onNavigateToStats: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    var showResetDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "WasteTimer",
            style = MaterialTheme.typography.headlineMedium
        )

        DashboardCard(
            title = "Current Session",
            value = formatTime(state.currentSessionMillis)
        )

        DashboardCard(
            title = "Total Wasted",
            value = formatTime(state.totalWastedMillis)
        )

        SessionCard(
            sessionCount = state.sessionCount
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onToggleService
        ) {
            Text(
                if (isServiceRunning)
                    "Stop Tracking"
                else
                    "Start Tracking"
            )
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                showResetDialog = true
            }
        ) {
            Text("Reset")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNavigateToHistory
        ) {
            Text("History")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNavigateToStats
        ) {
            Text("Statistics")
        }

    }

    if (showResetDialog) {
        ResetDialog(
            onConfirm = {
                viewModel.resetTracking()
                showResetDialog = false
            },
            onDismiss = {
                showResetDialog = false
            }
        )
    }
}

private fun formatTime(millis: Long): String {

    val seconds = (millis / 1000) % 60
    val minutes = (millis / 60000) % 60
    val hours = millis / 3600000

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
