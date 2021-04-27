package com.apps.bacon.pomodorotimer.viewmodel

import androidx.lifecycle.ViewModel
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import com.apps.bacon.pomodorotimer.data.repositories.WeeklyStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val weeklyStatsRepository: WeeklyStatsRepository
) : ViewModel() {
    fun getUserCustomTimeOfSession() = userRepository.getCustomTimeOfSession()

    fun increaseRunningSessions() = CoroutineScope(Dispatchers.Default).launch {
        userRepository.increaseRunningSessions()
    }

    fun increaseCompletedSessions() = CoroutineScope(Dispatchers.Default).launch {
        userRepository.increaseCompletedSessions()
    }

    fun increaseRunningSessionsOfDay(day: String) = CoroutineScope(Dispatchers.Default).launch {
        weeklyStatsRepository.increaseRunningSessionOfDay(day)
    }

    fun increaseCompletedSessionsOfDay(day: String) = CoroutineScope(Dispatchers.Default).launch {
        weeklyStatsRepository.increaseCompletedSessionOfDay(day)
    }
}