package com.example.wastetimer.data.model

data class DashboardState(

    /**
     * Current active tracking period ID.
     */
    val activePeriodId: Long = 0L,

    /**
     * Total wasted time in the current period.
     */
    val totalWastedMillis: Long = 0L,

    /**
     * Number of sessions in the current period.
     */
    val sessionCount: Int = 0,

    /**
     * Current running session.
     */
    val currentSessionMillis: Long = 0L,

    /**
     * Whether the foreground service is running.
     */
    val isTracking: Boolean = false
)
