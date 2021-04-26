package com.apps.bacon.pomodorotimer.util

import android.text.format.DateFormat
import java.util.*

class DateInfo {
    fun day(): String {
        return DateFormat.format("EEEE", Date()).toString().toLowerCase(Locale.getDefault())
    }

    fun today(): String {
        return DateFormat.format("dd-MM-yyyy", Date()) as String
    }

    fun getNextMonday(): String {
        val date = Calendar.getInstance()
        while (date[Calendar.DAY_OF_WEEK] != Calendar.MONDAY) {
            date.add(Calendar.DATE, 1)
        }

        return DateFormat.format("dd-MM-yyyy", date) as String
    }

    private fun getCurrentHour(): Int {
        return DateFormat.format("HH", Date()).toString().toInt()
    }

    private fun getCurrentMinutes(): Int {
        return DateFormat.format("mm", Date()).toString().toInt()
    }

    fun getTimeForNextDay(): Long {
        val minutesToMillis = 60000L
        val currentHour = getCurrentHour()
        val currentMinutes = getCurrentMinutes()
        val minutesToNextFullHour = if (currentMinutes == 0)
            0
        else
            60 - currentMinutes

        return if (currentHour == 23)
            minutesToNextFullHour * minutesToMillis
        else
            ((24 - currentHour) * 60 + minutesToNextFullHour) * minutesToMillis
    }
}