package com.apps.bacon.pomodorotimer.di

import android.content.Context
import com.apps.bacon.pomodorotimer.data.AppDatabase
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import com.apps.bacon.pomodorotimer.data.repositories.WeeklyStatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideUserRepository(
        database: AppDatabase
    ) = UserRepository(database)

    @Provides
    @Singleton
    fun provideWeeklyStatsRepository(
        database: AppDatabase
    ) = WeeklyStatsRepository(database)
}