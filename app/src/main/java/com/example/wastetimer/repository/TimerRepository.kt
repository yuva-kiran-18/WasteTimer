package com.example.wastetimer.repository

import com.example.wastetimer.data.local.ResetPeriodEntity
import com.example.wastetimer.data.local.SessionEntity
import com.example.wastetimer.data.local.TimerDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerRepository @Inject constructor(
    private val timerDao: TimerDao
) {
    suspend fun saveSession(periodId: Long, startTime: Long, endTime: Long) {
        require(endTime >= startTime) { "End time cannot be earlier than start time" }
    
        val duration = endTime - startTime
        val session = SessionEntity(
            periodId = periodId,
            startTimeEpochMillis = startTime,
            endTimeEpochMillis = endTime,
            durationMillis = duration
        )
        timerDao.insertSession(session)
}

    suspend fun getOrCreateActivePeriodId(): Long {
        val activePeriodId = timerDao.getLatestResetPeriodId()
        return if (activePeriodId != null) {
            activePeriodId
        } else {
            createNewResetPeriod()
        }
    }

    suspend fun createNewResetPeriod(): Long {
        val newPeriod = ResetPeriodEntity(
            resetDateEpochMillis = System.currentTimeMillis(),
            totalWastedTimeMillis = 0L,
            note = null
        )
        return timerDao.insertResetPeriod(newPeriod)
    }

    suspend fun resetTracking(): Long {
    val lastPeriodId = timerDao.getLatestResetPeriodId()

    if (lastPeriodId != null) {
        val total = timerDao.getPeriodTotalOnce(lastPeriodId) ?: 0L
        timerDao.updatePeriodTotal(lastPeriodId, total)
        timerDao.updatePeriodResetTotal(lastPeriodId, total)
    }

    return createNewResetPeriod()
}
    suspend fun getPeriodTotalOnce(periodId: Long): Long?
    fun getSessionsForPeriod(periodId: Long): Flow<List<SessionEntity>> {
        return timerDao.getSessionsForPeriod(periodId)
    }

    fun getTotalTimeForPeriod(periodId: Long): Flow<Long?> {
        return timerDao.getTotalTimeForPeriod(periodId)
    }
}
