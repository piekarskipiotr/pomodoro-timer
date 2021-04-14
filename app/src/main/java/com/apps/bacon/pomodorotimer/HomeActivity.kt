package com.apps.bacon.pomodorotimer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.apps.bacon.pomodorotimer.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.startTimerButton.setOnClickListener {
            startTimer()
        }

        binding.stopTimerButton.setOnClickListener {
            timer.cancel()
            onTimerFinished()
        }

        binding.userProfileButton.setOnClickListener {
            intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onTimerFinished() {
        resetUI()
    }

    private fun startTimer() {
        binding.circularProgressBar.progressMax = 1500f
        timer = object : CountDownTimer(1500000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateUI(millisUntilFinished)
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    private fun updateUI(ms: Long) {
        val minutes = (ms / 1000) / 60
        val seconds = (ms / 1000) % 60
        binding.timerText.text = String.format("%02d:%02d",  minutes, seconds)

        binding.circularProgressBar.apply {
            setProgressWithAnimation(progress + 1, 500)
        }
    }

    private fun resetUI() {
        binding.timerText.text = getString(R.string.time_zero)
        binding.circularProgressBar.apply {
            setProgressWithAnimation(0f, 500)
        }
    }

    override fun onBackPressed() {
        //no possibility of going back
    }
}