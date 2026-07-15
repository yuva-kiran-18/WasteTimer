package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.data.model.TrackingPeriodUiModel
import com.example.wastetimer.data.model.HistoryUiState
import com.example.wastetimer.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.wastetimer.data.model.SessionHistoryItem
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryState())
    val uiState: StateFlow<HistoryState> = _uiState

    init {
        loadHistory()
    }

    private fun loadHistory() {

        viewModelScope.launch {

            repository
                .getTrackingPeriodsWithSessions()
                .collect { periods ->

                    _uiState.value = HistoryState(

                        periods = periods.map { period ->
                        
                            HistoryItem(
                        
                                periodId = period.trackingPeriod.id,
                        
                                createdAt = period.trackingPeriod.createdAt,
                        
                                endedAt = period.trackingPeriod.endedAt,
                        
                                totalDuration = period.trackingPeriod.totalDurationMillis,
                        
                                sessionCount = period.sessions.size,
                        
                                sessions = period.sessions.map {
                        
                                    SessionHistoryItem(
                        
                                        sessionId = it.id,
                        
                                        startTime = it.startTime,
                        
                                        endTime = it.endTime,
                        
                                        duration = it.duration
                        
                                    )
                        
                                }
                        
                            )
                        
                        },

                        isLoading = false

                    )

                }

        }

    }
    fun toggleExpanded(
    periodId: Long
    ) {

    _uiState.value = _uiState.value.copy(

        periods = _uiState.value.periods.map {

            if (it.periodId == periodId) {

                it.copy(
                    expanded = !it.expanded
                )

            } else it

        }

    )

}    
    fun deletePeriod(periodId: Long) {

        viewModelScope.launch {

            repository.deleteTrackingPeriod(periodId)

        }

    }

}
