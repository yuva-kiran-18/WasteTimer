package com.example.wastetimer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracking_periods")
data class TrackingPeriodEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * Time when this tracking period started.
     * Created when the app is first installed
     * or after every Reset.
     */
    val createdAt: Long,

    /**
     * Time when Reset was pressed.
     * Null while this is the active period.
     */
    val endedAt: Long? = null,

    /**
     * Cached total duration for this period.
     * Updated only when the period is closed.
     */
    val totalDurationMillis: Long = 0L
)
