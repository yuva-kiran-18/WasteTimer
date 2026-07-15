package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.data.model.DashboardState
import com.example.wastetimer.repository.TimerRepository
import com.example.wastetimer.service.TimerForegroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val dashboardInfo = MutableStateFlow(
        DashboardState()
    )

    val uiState: StateFlow<DashboardState> =
        combine(
            dashboardInfo,
            TimerForegroundService.elapsedTime
        ) { state, elapsed ->

            state.copy(
                currentSessionMillis = elapsed,
                isTracking = TimerForegroundService.isRunning
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState()
        )
}
