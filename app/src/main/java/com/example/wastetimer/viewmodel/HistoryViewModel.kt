package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.data.model.HistoryItem
import com.example.wastetimer.data.model.HistoryState
import com.example.wastetimer.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

                        periods = periods.map {

                            HistoryItem(

                                periodId = it.trackingPeriod.id,

                                startedAt = it.trackingPeriod.createdAt,

                                endedAt = it.trackingPeriod.endedAt,

                                totalDuration = it.trackingPeriod.totalDurationMillis,

                                sessionCount = it.sessions.size

                            )

                        },

                        isLoading = false

                    )

                }

        }

    }

    fun deletePeriod(periodId: Long) {

        viewModelScope.launch {

            repository.deleteTrackingPeriod(periodId)

        }

    }

}
