package com.apps.bacon.pomodorotimer.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apps.bacon.pomodorotimer.data.entities.User

@Dao
interface UserDao {
    @Query("SELECT user_name FROM user")
    fun getUserName(): String

    @Query("SELECT completed_sessions FROM user")
    fun getCompletedSessions(): LiveData<Int>

    @Query("SELECT running_sessions FROM user")
    fun getRunningSessions(): LiveData<Int>

    @Query("UPDATE user SET completed_sessions = completed_sessions + 1")
    suspend fun increaseCompletedSessions()

    @Query("UPDATE user SET running_sessions = running_sessions + 1")
    suspend fun increaseRunningSessions()

    @Query("SELECT custom_time_of_session FROM user")
    fun getCustomTimeOfSession(): LiveData<Long>

    @Query("UPDATE user SET custom_time_of_session = :time")
    suspend fun updateCustomTimeOfSession(time: Long)

    @Insert
    suspend fun insert(user: User)
}