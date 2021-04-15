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

        updateStopButton(false, DISABLE_BUTTON_ALPHA)
    }

    private fun onTimerFinished() {
        updateStopButton(false, DISABLE_BUTTON_ALPHA)
        updateStartButton(true, ENABLE_BUTTON_ALPHA)
        resetUI()
    }

    private fun startTimer() {
        updateStartButton(false, DISABLE_BUTTON_ALPHA)
        updateStopButton(true, ENABLE_BUTTON_ALPHA)

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

    private fun updateStartButton(isClickable: Boolean, alpha: Float){
        binding.startTimerButton.apply {
            this.isClickable = isClickable
            this.alpha = alpha
        }
    }

    private fun updateStopButton(isClickable: Boolean, alpha: Float){
        binding.stopTimerButton.apply {
            this.isClickable = isClickable
            this.alpha = alpha
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

    companion object {
        private const val DISABLE_BUTTON_ALPHA = 0.9f
        private const val ENABLE_BUTTON_ALPHA = 1f
    }
}