package com.apps.bacon.pomodorotimer.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.apps.bacon.pomodorotimer.data.entities.WeeklyStats

@Dao
interface WeeklyStatsDao {
    @Query("SELECT * FROM weekly_stats ORDER BY week DESC LIMIT 1")
    fun getCurrentWeekStats(): LiveData<WeeklyStats>

    @Query("SELECT MAX(week) FROM weekly_stats")
    fun getCurrentWeekNumber(): Int

    @Query("UPDATE weekly_stats SET monday_running = monday_running + 1 WHERE week = :week")
    suspend fun increaseMondayRunning(week: Int)

    @Query("UPDATE weekly_stats SET monday_completed = monday_completed + 1 WHERE week = :week")
    suspend fun increaseMondayCompleted(week: Int)

    @Query("UPDATE weekly_stats SET tuesday_running = tuesday_running + 1 WHERE week = :week")
    suspend fun increaseTuesdayRunning(week: Int)

    @Query("UPDATE weekly_stats SET tuesday_completed = tuesday_completed + 1 WHERE week = :week")
    suspend fun increaseTuesdayCompleted(week: Int)

    @Query("UPDATE weekly_stats SET wednesday_running = wednesday_running + 1 WHERE week = :week")
    suspend fun increaseWednesdayRunning(week: Int)

    @Query("UPDATE weekly_stats SET wednesday_completed = wednesday_completed + 1 WHERE week = :week")
    suspend fun increaseWednesdayCompleted(week: Int)

    @Query("UPDATE weekly_stats SET thursday_running = thursday_running + 1 WHERE week = :week")
    suspend fun increaseThursdayRunning(week: Int)

    @Query("UPDATE weekly_stats SET thursday_completed = thursday_completed + 1 WHERE week = :week")
    suspend fun increaseThursdayCompleted(week: Int)

    @Query("UPDATE weekly_stats SET friday_running = friday_running + 1 WHERE week = :week")
    suspend fun increaseFridayRunning(week: Int)

    @Query("UPDATE weekly_stats SET friday_completed = friday_completed + 1 WHERE week = :week")
    suspend fun increaseFridayCompleted(week: Int)

    @Query("UPDATE weekly_stats SET saturday_running = saturday_running + 1 WHERE week = :week")
    suspend fun increaseSaturdayRunning(week: Int)

    @Query("UPDATE weekly_stats SET saturday_completed = saturday_completed + 1 WHERE week = :week")
    suspend fun increaseSaturdayCompleted(week: Int)

    @Query("UPDATE weekly_stats SET saturday_running = saturday_running + 1 WHERE week = :week")
    suspend fun increaseSundayRunning(week: Int)

    @Query("UPDATE weekly_stats SET sunday_completed = sunday_completed + 1 WHERE week = :week")
    suspend fun increaseSundayCompleted(week: Int)

    @Transaction
    suspend fun increaseRunningSessionOfDay(day: String) {
        val weekNumber = getCurrentWeekNumber()
        when (day) {
            "monday" -> increaseMondayRunning(weekNumber)
            "tuesday" -> increaseTuesdayRunning(weekNumber)
            "wednesday" -> increaseWednesdayRunning(weekNumber)
            "thursday" -> increaseThursdayRunning(weekNumber)
            "friday" -> increaseFridayRunning(weekNumber)
            "saturday" -> increaseSaturdayRunning(weekNumber)
            "sunday" -> increaseSundayRunning(weekNumber)
        }
    }

    @Transaction
    suspend fun increaseCompletedSessionOfDay(day: String) {
        val weekNumber = getCurrentWeekNumber()
        when (day) {
            "monday" -> increaseMondayCompleted(weekNumber)
            "tuesday" -> increaseTuesdayCompleted(weekNumber)
            "wednesday" -> increaseWednesdayCompleted(weekNumber)
            "thursday" -> increaseThursdayCompleted(weekNumber)
            "friday" -> increaseFridayCompleted(weekNumber)
            "saturday" -> increaseSaturdayCompleted(weekNumber)
            "sunday" -> increaseSundayCompleted(weekNumber)
        }
    }

    @Insert
    suspend fun insert(weeklyStats: WeeklyStats)
}