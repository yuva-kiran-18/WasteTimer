package com.example.wastetimer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.wastetimer.data.local.entity.TrackingPeriodEntity
import com.example.wastetimer.data.local.relation.TrackingPeriodWithSessions
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackingPeriodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeriod(period: TrackingPeriodEntity): Long

    @Query("""
        SELECT *
        FROM tracking_periods
        WHERE endedAt IS NULL
        ORDER BY createdAt DESC
        LIMIT 1
    """)
    suspend fun getActivePeriod(): TrackingPeriodEntity?

    @Query("""
        SELECT *
        FROM tracking_periods
        ORDER BY createdAt DESC
    """)
    fun getAllPeriods(): Flow<List<TrackingPeriodEntity>>

    @Transaction
    @Query("""
        SELECT *
        FROM tracking_periods
        ORDER BY createdAt DESC
    """)
    fun getPeriodsWithSessions(): Flow<List<TrackingPeriodWithSessions>>

    @Query("""
        UPDATE tracking_periods
        SET
            endedAt = :endedAt,
            totalDurationMillis = :totalDuration
        WHERE id = :periodId
    """)
    suspend fun closePeriod(
        periodId: Long,
        endedAt: Long,
        totalDuration: Long
    )

    @Query("""
        DELETE FROM tracking_periods
        WHERE id = :periodId
    """)
    suspend fun deletePeriod(periodId: Long)
}
