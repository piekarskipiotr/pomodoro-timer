package com.apps.bacon.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apps.bacon.pomodorotimer.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}