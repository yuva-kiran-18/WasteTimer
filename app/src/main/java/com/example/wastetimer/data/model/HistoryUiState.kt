package com.example.wastetimer.data.model

data class HistoryUiState(

    val periods: List<TrackingPeriodUiModel> = emptyList(),

    val isLoading: Boolean = true,

    val selectedPeriodId: Long? = null

)
