package com.example.wastetimer.repository

import com.example.wastetimer.data.local.dao.SessionDao
import com.example.wastetimer.data.local.dao.TrackingPeriodDao
import com.example.wastetimer.data.local.entity.SessionEntity
import com.example.wastetimer.data.local.entity.TrackingPeriodEntity
import com.example.wastetimer.data.local.relation.TrackingPeriodWithSessions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerRepository @Inject constructor(
    private val sessionDao: SessionDao,
    private val trackingPeriodDao: TrackingPeriodDao
) {

    suspend fun getOrCreateActivePeriod(): TrackingPeriodEntity {

        val existing = trackingPeriodDao.getActivePeriod()

        if (existing != null) {
            return existing
        }

        val id = trackingPeriodDao.insertPeriod(
            TrackingPeriodEntity(
                createdAt = System.currentTimeMillis()
            )
        )

        return TrackingPeriodEntity(
            id = id,
            createdAt = System.currentTimeMillis()
        )
    }

    suspend fun saveSession(
        startTime: Long,
        endTime: Long
    ) {

        val period = getOrCreateActivePeriod()

        sessionDao.insertSession(
            SessionEntity(
                trackingPeriodId = period.id,
                startTime = startTime,
                endTime = endTime,
                duration = endTime - startTime
            )
        )
    }

    suspend fun resetTracking() {

        val active = trackingPeriodDao.getActivePeriod()
            ?: return

        val totalDuration =
            sessionDao.getTotalDuration(active.id)

        trackingPeriodDao.closePeriod(
            periodId = active.id,
            endedAt = System.currentTimeMillis(),
            totalDuration = totalDuration
        )

        trackingPeriodDao.insertPeriod(
            TrackingPeriodEntity(
                createdAt = System.currentTimeMillis()
            )
        )
    }

    fun getAllTrackingPeriods():
            Flow<List<TrackingPeriodEntity>> =
        trackingPeriodDao.getAllPeriods()

    fun getTrackingPeriodsWithSessions():
            Flow<List<TrackingPeriodWithSessions>> =
        trackingPeriodDao.getPeriodsWithSessions()

    fun getSessionsForPeriod(
        periodId: Long
    ): Flow<List<SessionEntity>> =
        sessionDao.getSessionsForPeriod(periodId)
}
