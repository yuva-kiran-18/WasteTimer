package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.repository.TimerRepository
import com.example.wastetimer.service.TimerForegroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    // Observe the live timer from the foreground service
    val currentSessionMillis: StateFlow<Long> = TimerForegroundService.elapsedTime
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )

    // Formats the milliseconds into HH:MM:SS for the UI
    val formattedCurrentSession: StateFlow<String> = currentSessionMillis.map { formatTime(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "00:00:00"
        )

    // TODO: In a full implementation, we will fetch the active periodId from DataStore
    // For now, we simulate a mock active period ID of 1
    private val activePeriodId = 1L 

    fun resetTimer() {
        viewModelScope.launch {
            val currentWasted = currentSessionMillis.value
            if (currentWasted > 0) {
                // 1. Save the final session
                val endTime = System.currentTimeMillis()
                val startTime = endTime - currentWasted
                repository.saveSession(activePeriodId, startTime, endTime)
            }
            // 2. Create a brand new tracking period (History record)
            repository.createNewResetPeriod()
            
            // Note: Stopping the service zeroes out the current timer
        }
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60))
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}
