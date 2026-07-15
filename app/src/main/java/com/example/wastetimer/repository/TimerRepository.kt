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

        trackingPeriodDao.getActivePeriod()?.let {
            return it
        }

        val now = System.currentTimeMillis()

        val id = trackingPeriodDao.insertPeriod(
            TrackingPeriodEntity(
                createdAt = now
            )
        )

        return TrackingPeriodEntity(
            id = id,
            createdAt = now
        )
    }

    suspend fun saveSession(
        startTime: Long,
        endTime: Long
    ) {

        require(endTime >= startTime)

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

        val active =
            trackingPeriodDao.getActivePeriod()
                ?: return

        val total =
            sessionDao.getTotalDuration(active.id)

        trackingPeriodDao.closePeriod(
            periodId = active.id,
            endedAt = System.currentTimeMillis(),
            totalDuration = total
        )

        trackingPeriodDao.insertPeriod(
            TrackingPeriodEntity(
                createdAt = System.currentTimeMillis()
            )
        )
    }

    fun observeTrackingPeriods(): Flow<List<TrackingPeriodEntity>> =
        trackingPeriodDao.getAllPeriods()

    fun getTrackingPeriodsWithSessions(): Flow<List<TrackingPeriodWithSessions>> =
        trackingPeriodDao.getPeriodsWithSessions()

    fun getSessionsForPeriod(
        periodId: Long
    ): Flow<List<SessionEntity>> =
        sessionDao.getSessionsForPeriod(periodId)

    suspend fun getActiveTrackingPeriod(): TrackingPeriodEntity? =
        trackingPeriodDao.getActivePeriod()

    suspend fun getCurrentPeriodTotalDuration(): Long {

        val period =
            trackingPeriodDao.getActivePeriod()
                ?: return 0L

        return sessionDao.getTotalDuration(period.id)
    }

    suspend fun getCurrentSessionCount(): Int {

        val period =
            trackingPeriodDao.getActivePeriod()
                ?: return 0

        return sessionDao.getSessionCount(period.id)
    }

    suspend fun deleteTrackingPeriod(
        periodId: Long
    ) {

        sessionDao.deleteSessions(periodId)
        trackingPeriodDao.deletePeriod(periodId)

    }
}
