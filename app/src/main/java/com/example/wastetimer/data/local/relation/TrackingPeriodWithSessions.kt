package com.example.wastetimer.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.wastetimer.data.local.entity.SessionEntity
import com.example.wastetimer.data.local.entity.TrackingPeriodEntity

data class TrackingPeriodWithSessions(

    @Embedded
    val trackingPeriod: TrackingPeriodEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "trackingPeriodId"
    )
    val sessions: List<SessionEntity>
)
