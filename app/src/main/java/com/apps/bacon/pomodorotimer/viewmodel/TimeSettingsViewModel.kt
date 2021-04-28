package com.apps.bacon.pomodorotimer.viewmodel

import androidx.lifecycle.ViewModel
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeSettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getCustomTimeOfSession() = userRepository.getCustomTimeOfSession()

    fun updateCustomTimeOfSession(time: Long) = CoroutineScope(Dispatchers.Default).launch {
        userRepository.updateCustomTimeOfSession(time)
    }
}