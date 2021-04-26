package com.apps.bacon.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apps.bacon.pomodorotimer.databinding.ActivityTimeSettingsBinding

class TimeSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}