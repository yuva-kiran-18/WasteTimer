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
        val duration = endTime - startTime
        val session = SessionEntity(
            periodId = periodId,
            startTimeEpochMillis = startTime,
            endTimeEpochMillis = endTime,
            durationMillis = duration
        )
        timerDao.insertSession(session)
    }

    suspend fun createNewResetPeriod(): Long {
        val newPeriod = ResetPeriodEntity(
            resetDateEpochMillis = System.currentTimeMillis(),
            totalWastedTimeMillis = 0L
        )
        return timerDao.insertResetPeriod(newPeriod)
    }

    fun getSessionsForPeriod(periodId: Long): Flow<List<SessionEntity>> {
        return timerDao.getSessionsForPeriod(periodId)
    }

    fun getTotalTimeForPeriod(periodId: Long): Flow<Long?> {
        return timerDao.getTotalTimeForPeriod(periodId)
    }
}
