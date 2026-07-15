package com.example.wastetimer.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "reset_periods")
data class ResetPeriodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val resetDateEpochMillis: Long,
    val totalWastedTimeMillis: Long,
    val note: String? = null
)

@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = ResetPeriodEntity::class,
            parentColumns = ["id"],
            childColumns = ["periodId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("periodId")]
)
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val periodId: Long,
    val startTimeEpochMillis: Long,
    val endTimeEpochMillis: Long,
    val durationMillis: Long
)

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResetPeriod(period: ResetPeriodEntity): Long

    @Query("SELECT * FROM sessions WHERE periodId = :periodId ORDER BY startTimeEpochMillis DESC")
    fun getSessionsForPeriod(periodId: Long): Flow<List<SessionEntity>>

    @Query("SELECT SUM(durationMillis) FROM sessions WHERE periodId = :periodId")
    fun getTotalTimeForPeriod(periodId: Long): Flow<Long?>

    @Query("SELECT id FROM reset_periods ORDER BY resetDateEpochMillis DESC LIMIT 1")
    suspend fun getLatestResetPeriodId(): Long?

    @Query("SELECT SUM(durationMillis) FROM sessions WHERE periodId = :periodId")
    suspend fun getPeriodTotalOnce(periodId: Long): Long?

    @Query("SELECT SUM(durationMillis) FROM sessions WHERE periodId = (SELECT id FROM reset_periods ORDER BY resetDateEpochMillis DESC LIMIT 1)")
    suspend fun getLatestPeriodTotalOnce(): Long?

    @Query("UPDATE reset_periods SET totalWastedTimeMillis = :total WHERE id = :periodId")
    suspend fun updatePeriodTotal(periodId: Long, total: Long)

    @Query("UPDATE reset_periods SET totalWastedTimeMillis = :total WHERE id = :periodId")
    suspend fun updatePeriodResetTotal(periodId: Long, total: Long)
}

@Database(entities = [ResetPeriodEntity::class, SessionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
}
