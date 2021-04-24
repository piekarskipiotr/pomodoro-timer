package com.apps.bacon.pomodorotimer.data.repositories

import com.apps.bacon.pomodorotimer.data.AppDatabase
import com.apps.bacon.pomodorotimer.data.entities.WeeklyStats
import javax.inject.Inject

class WeeklyStatsRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getCurrentWeekStats() = database.weeklyStatsDao().getCurrentWeekStats()

    fun getCurrentWeekNumber() = database.weeklyStatsDao().getCurrentWeekNumber()

    suspend fun increaseRunningSessionOfDay(day: String) = database.weeklyStatsDao().increaseRunningSessionOfDay(day)

    suspend fun increaseCompletedSessionOfDay(day: String) = database.weeklyStatsDao().increaseCompletedSessionOfDay(day)

    suspend fun insert(weeklyStats: WeeklyStats) = database.weeklyStatsDao().insert(weeklyStats)
}