package com.example.wastetimer.di

import android.content.Context
import androidx.room.Room
import com.example.wastetimer.data.local.AppDatabase
import com.example.wastetimer.data.local.dao.SessionDao
import com.example.wastetimer.data.local.dao.TrackingPeriodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "waste_timer_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSessionDao(
        database: AppDatabase
    ): SessionDao {
        return database.sessionDao()
    }

    @Provides
    @Singleton
    fun provideTrackingPeriodDao(
        database: AppDatabase
    ): TrackingPeriodDao {
        return database.trackingPeriodDao()
    }
}
