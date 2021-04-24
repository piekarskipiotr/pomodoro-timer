package com.apps.bacon.pomodorotimer.viewmodel

import androidx.lifecycle.ViewModel
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import com.apps.bacon.pomodorotimer.data.repositories.WeeklyStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val weeklyStatsRepository: WeeklyStatsRepository
) : ViewModel() {
    fun getUserName() = userRepository.getUserName()

    fun getRunningSessions() = userRepository.getRunningSessions()

    fun getCompletedSessions() = userRepository.getCompletedSessions()

    fun getCurrentWeekStats() = weeklyStatsRepository.getCurrentWeekStats()
}