package com.apps.bacon.pomodorotimer.data.repositories

import com.apps.bacon.pomodorotimer.data.AppDatabase
import com.apps.bacon.pomodorotimer.data.entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getUserName() = database.userDao().getUserName()

    fun getCompletedSessions() = database.userDao().getCompletedSessions()

    fun getRunningSessions() = database.userDao().getRunningSessions()

    suspend fun increaseCompletedSessions() = database.userDao().increaseCompletedSessions()

    suspend fun increaseRunningSessions() = database.userDao().increaseRunningSessions()

    fun getCustomTimeOfSession() = database.userDao().getCustomTimeOfSession()

    suspend fun updateCustomTimeOfSession(time: Long) = database.userDao().updateCustomTimeOfSession(time)

    suspend fun insert(user: User) = database.userDao().insert(user)
}