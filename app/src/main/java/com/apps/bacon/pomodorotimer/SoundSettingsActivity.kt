package com.apps.bacon.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apps.bacon.pomodorotimer.databinding.ActivitySoundSettingsBinding

class SoundSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoundSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoundSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}