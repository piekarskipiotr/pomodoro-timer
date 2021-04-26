package com.apps.bacon.pomodorotimer.util

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.apps.bacon.pomodorotimer.data.entities.WeeklyStats
import com.apps.bacon.pomodorotimer.data.repositories.WeeklyStatsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DateService : Service() {
    @Inject
    lateinit var repository: WeeklyStatsRepository
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        runServiceTask()

        return START_STICKY
    }

    private fun runServiceTask() {
        val delay = DateInfo().getTimeForNextDay()
        Handler(Looper.getMainLooper()).postDelayed({
            checkForDayChange()
            runServiceTask()
        }, delay)
    }

    private fun checkForDayChange() {
        val today = DateInfo().today()
        val sharedPreference = getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)
        var newWeekDate = sharedPreference.getString(NEW_WEEK_DATE_KEY, "")
        if (today == newWeekDate) {
            val newWeekNumber = repository.getCurrentWeekNumber() + 1
            val weeklyStats = WeeklyStats(
                newWeekNumber,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
            CoroutineScope(Dispatchers.Default).launch {
                repository.insert(weeklyStats)
            }

            newWeekDate = DateInfo().getNextMonday()
            with(sharedPreference.edit()) {
                putString(NEW_WEEK_DATE_KEY, newWeekDate)
                apply()
            }
        }
    }

    companion object {
        //sharedPreference keys
        const val APP_PREFERENCES_KEY = "APP_PREFERENCES"
        const val NEW_WEEK_DATE_KEY = "NEW_WEEK_DATE"
    }
}