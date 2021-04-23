package com.apps.bacon.pomodorotimer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_stats")
data class WeeklyStats (
    @PrimaryKey
    @ColumnInfo(name = "week")
    var week: Int,

    @ColumnInfo(name = "monday_running")
    var mondayRunning: Int,

    @ColumnInfo(name = "monday_completed")
    var mondayCompleted: Int,

    @ColumnInfo(name = "tuesday_running")
    var tuesdayRunning: Int,

    @ColumnInfo(name = "tuesday_completed")
    var tuesdayCompleted: Int,

    @ColumnInfo(name = "wednesday_running")
    var wednesdayRunning: Int,

    @ColumnInfo(name = "wednesday_completed")
    var wednesdayCompleted: Int,

    @ColumnInfo(name = "thursday_running")
    var thursdayRunning: Int,

    @ColumnInfo(name = "thursday_completed")
    var thursdayCompleted: Int,

    @ColumnInfo(name = "friday_running")
    var fridayRunning: Int,

    @ColumnInfo(name = "friday_completed")
    var fridayCompleted: Int,

    @ColumnInfo(name = "saturday_running")
    var saturdayRunning: Int,

    @ColumnInfo(name = "saturday_completed")
    var saturdayCompleted: Int,

    @ColumnInfo(name = "sunday_running")
    var sundayRunning: Int,

    @ColumnInfo(name = "sunday_completed")
    var sundayCompleted: Int
)