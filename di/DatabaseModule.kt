package com.example.wastetimer.di

import android.content.Context
import androidx.room.Room
import com.example.wastetimer.data.local.AppDatabase
import com.example.wastetimer.data.local.TimerDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "waste_timer_db"
        )
        .fallbackToDestructiveMigration() // We will handle proper migrations later
        .build()
    }

    @Provides
    @Singleton
    fun provideTimerDao(database: AppDatabase): TimerDao {
        return database.timerDao()
    }
}
