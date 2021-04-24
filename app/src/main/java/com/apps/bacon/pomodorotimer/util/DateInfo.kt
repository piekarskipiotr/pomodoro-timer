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
}