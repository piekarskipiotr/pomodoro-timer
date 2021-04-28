package com.apps.bacon.pomodorotimer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey
    @ColumnInfo(name = "alarm_key")
    var key: Int,

    @ColumnInfo(name = "one_alarm")
    var alarmOne: Int,

    @ColumnInfo(name = "two_alarm")
    var alarmTwo: Int,

    @ColumnInfo(name = "recorded_alarm")
    var recordedAlarm: String?
)