package com.example.wastetimer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastetimer.data.model.HistoryUiState
import com.example.wastetimer.data.model.SessionUiModel
import com.example.wastetimer.data.model.TrackingPeriodUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.wastetimer.data.repository.TimerRepository

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        observeHistory()
    }

    private fun observeHistory() {

        viewModelScope.launch {

            repository
                .getTrackingPeriodsWithSessions()
                .collect { periods: List<TrackingPeriodWithSessions> ->
                    _uiState.value = HistoryUiState(

                        periods = periods.map { period: TrackingPeriodWithSessions ->

                            TrackingPeriodUiModel(

                                periodId = period.trackingPeriod.id,

                                createdAt = period.trackingPeriod.createdAt,

                                endedAt = period.trackingPeriod.endedAt,

                                totalDuration = period.trackingPeriod.totalDurationMillis,

                                sessionCount = period.sessions.size,

                                sessions = period.sessions.map { session: SessionEntity ->

                                    SessionUiModel(

                                        sessionId = session.id,

                                        startTime = session.startTime,

                                        endTime = session.endTime,

                                        duration = session.duration

                                    )

                                }

                            )

                        },

                        isLoading = false

                    )

                }

        }

    }

    fun toggleExpanded(periodId: Long) {

        _uiState.value = _uiState.value.copy(

            periods = _uiState.value.periods.map {

                if (it.periodId == periodId) {

                    it.copy(
                        expanded = !it.expanded
                    )

                } else {

                    it

                }

            }

        )

    }

    fun deletePeriod(periodId: Long) {

        viewModelScope.launch {

            repository.deleteTrackingPeriod(periodId)

        }

    }
    fun refresh() = observeHistory()

}
