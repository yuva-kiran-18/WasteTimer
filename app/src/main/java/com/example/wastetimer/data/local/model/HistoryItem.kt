package com.example.wastetimer.data.model

data class HistoryItem(

    val periodId: Long,

    val startedAt: Long,

    val endedAt: Long?,

    val totalDuration: Long,

    val sessionCount: Int,

    val expanded: Boolean = false
)
