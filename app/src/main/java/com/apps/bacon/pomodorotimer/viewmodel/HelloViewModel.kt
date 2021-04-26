package com.apps.bacon.pomodorotimer.viewmodel

import androidx.lifecycle.ViewModel
import com.apps.bacon.pomodorotimer.data.entities.User
import com.apps.bacon.pomodorotimer.data.entities.WeeklyStats
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import com.apps.bacon.pomodorotimer.data.repositories.WeeklyStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelloViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val weeklyStatsRepository: WeeklyStatsRepository,
) : ViewModel() {
    fun insertUser(user: User) = CoroutineScope(Dispatchers.Default).launch{
        userRepository.insert(user)
    }

    fun insertWeeklyStats(weeklyStats: WeeklyStats) = CoroutineScope(Dispatchers.Default).launch{
        weeklyStatsRepository.insert(weeklyStats)
    }
}