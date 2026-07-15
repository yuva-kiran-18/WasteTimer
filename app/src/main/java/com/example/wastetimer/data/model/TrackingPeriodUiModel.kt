package com.example.wastetimer.data.model

data class TrackingPeriodUiModel(

    val periodId: Long,

    val createdAt: Long,

    val endedAt: Long?,

    val totalDuration: Long,

    val sessionCount: Int,

    val sessions: List<SessionUiModel>,

    val expanded: Boolean = false

)
