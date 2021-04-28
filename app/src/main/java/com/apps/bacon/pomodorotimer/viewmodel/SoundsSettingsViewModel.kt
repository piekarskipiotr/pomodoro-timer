package com.apps.bacon.pomodorotimer.viewmodel

import androidx.lifecycle.ViewModel
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoundSettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getAlarmSound() = userRepository.getAlarmSound()

    fun updateSelectedAlarm(alarmIndex: Int) = CoroutineScope(Dispatchers.Default).launch {
        userRepository.updateSelectedAlarm(alarmIndex)
    }
}