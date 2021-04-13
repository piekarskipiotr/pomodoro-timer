package com.apps.bacon.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apps.bacon.pomodorotimer.databinding.ActivityHelloBinding

class HelloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}