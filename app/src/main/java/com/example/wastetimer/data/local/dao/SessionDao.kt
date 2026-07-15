package com.example.wastetimer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wastetimer.data.local.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<SessionEntity>)

    @Query("""
        SELECT *
        FROM sessions
        WHERE trackingPeriodId = :periodId
        ORDER BY startTime DESC
    """)
    fun getSessionsForPeriod(periodId: Long): Flow<List<SessionEntity>>

    @Query("""
        SELECT *
        FROM sessions
        ORDER BY startTime DESC
    """)
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("""
        SELECT COALESCE(SUM(duration), 0)
        FROM sessions
        WHERE trackingPeriodId = :periodId
    """)
    suspend fun getTotalDuration(periodId: Long): Long

    @Query("""
        SELECT COUNT(*)
        FROM sessions
        WHERE trackingPeriodId = :periodId
    """)
    suspend fun getSessionCount(periodId: Long): Int

    @Query("""
        SELECT MAX(duration)
        FROM sessions
        WHERE trackingPeriodId = :periodId
    """)
    suspend fun getLongestSession(periodId: Long): Long?

    @Query("""
        SELECT MIN(duration)
        FROM sessions
        WHERE trackingPeriodId = :periodId
    """)
    suspend fun getShortestSession(periodId: Long): Long?

    @Query("""
        DELETE FROM sessions
        WHERE trackingPeriodId = :periodId
    """)
    suspend fun deleteSessions(periodId: Long)

    @Query("""
        DELETE FROM sessions
    """)
    suspend fun deleteAllSessions()
}
