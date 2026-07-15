package com.example.wastetimer.data.model

data class HistoryItem(

    val periodId: Long,

    val createdAt: Long,

    val endedAt: Long?,

    val totalDuration: Long,

    val sessionCount: Int,

    val sessions: List<SessionHistoryItem> = emptyList(),

    val expanded: Boolean = false

)

data class SessionHistoryItem(

    val sessionId: Long,

    val startTime: Long,

    val endTime: Long,

    val duration: Long

)
