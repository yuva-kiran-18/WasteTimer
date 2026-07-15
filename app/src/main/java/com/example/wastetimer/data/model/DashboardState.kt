package com.example.wastetimer.data.model

data class DashboardState(
    val activePeriodId: Long = 0L,
    val totalWastedMillis: Long = 0L,
    val sessionCount: Int = 0,
    val currentSessionMillis: Long = 0L,
    val isTracking: Boolean = false
)
