package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.data.model.DashboardState
import com.example.wastetimer.data.repository.TimerRepository
import com.example.wastetimer.service.TimerForegroundService
import com.example.wastetimer.utils.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        observeDashboard()
    }

    private fun observeDashboard() {

        viewModelScope.launch {

            combine(

                TimerForegroundService.elapsedTime,

                repository.observeTrackingPeriods()

            ) { elapsed, _ ->

                val total = repository.getCurrentPeriodTotalDuration()
                val sessions = repository.getCurrentSessionCount()

                DashboardState(

                    currentSession = TimeFormatter.formatDuration(elapsed),

                    totalTime = TimeFormatter.formatDuration(total),

                    sessionCount = sessions,

                    isTracking = TimerForegroundService.isRunning

                )

            }.collect {

                _uiState.value = it

            }

        }

    }

    fun resetTracking() {

        viewModelScope.launch {

            repository.resetTracking()

        }

    }

}
