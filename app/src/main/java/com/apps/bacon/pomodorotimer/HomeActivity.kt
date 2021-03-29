package com.apps.bacon.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apps.bacon.pomodorotimer.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}