package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.data.model.DashboardState
import com.example.wastetimer.data.repository.TimerRepository
import com.example.wastetimer.service.TimerForegroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState

    init {
        observeDashboard()
    }

    private fun observeDashboard() {

        viewModelScope.launch {

            while (true) {

                val activePeriod =
                    repository.getActiveTrackingPeriod()

                val totalDuration =
                    repository.getCurrentPeriodTotalDuration()

                val sessionCount =
                    repository.getCurrentSessionCount()

                _uiState.value = DashboardState(
                    activePeriodId = activePeriod?.id ?: 0L,
                    totalWastedMillis = totalDuration,
                    sessionCount = sessionCount,
                    currentSessionMillis = TimerForegroundService.elapsedTime.value,
                    isTracking = TimerForegroundService.isRunning
                )

                delay(1000)
            }
        }
    }

    fun resetTracking() {
        viewModelScope.launch {
            repository.resetTracking()
        }
    }
}
