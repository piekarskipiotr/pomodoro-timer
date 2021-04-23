package com.apps.bacon.pomodorotimer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_name")
    var name: String,

    @ColumnInfo(name = "week")
    var week: Int,

    @ColumnInfo(name = "running_sessions")
    var runningSessions: Int,

    @ColumnInfo(name = "completed_sessions")
    var completedSessions: Int
)