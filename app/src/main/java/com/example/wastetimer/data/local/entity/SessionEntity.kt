package com.example.wastetimer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = TrackingPeriodEntity::class,
            parentColumns = ["id"],
            childColumns = ["trackingPeriodId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("trackingPeriodId"),
        Index("startTime"),
        Index("endTime")
    ]
)
data class SessionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val trackingPeriodId: Long,

    val startTime: Long,

    val endTime: Long,

    /**
     * Cached duration in milliseconds.
     */
    val duration: Long
)
