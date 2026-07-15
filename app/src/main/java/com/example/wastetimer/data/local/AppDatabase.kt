package com.example.wastetimer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wastetimer.data.local.dao.SessionDao
import com.example.wastetimer.data.local.dao.TrackingPeriodDao
import com.example.wastetimer.data.local.entity.SessionEntity
import com.example.wastetimer.data.local.entity.TrackingPeriodEntity

@Database(
    entities = [
        TrackingPeriodEntity::class,
        SessionEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackingPeriodDao(): TrackingPeriodDao

    abstract fun sessionDao(): SessionDao
}
