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

    val currentSessionMillis: StateFlow<Long> =
        TimerForegroundService.elapsedTime
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0L
            )

    val formattedCurrentSession: StateFlow<String> =
        currentSessionMillis
            .map(::formatTime)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = "00:00"
            )

    fun resetTimer() {
        viewModelScope.launch {
            repository.resetTracking()
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
}
