package com.apps.bacon.pomodorotimer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.apps.bacon.pomodorotimer.databinding.ActivityHomeBinding
import com.apps.bacon.pomodorotimer.databinding.DialogSetTimerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var timer: CountDownTimer

    enum class Time(val milliseconds: Long) {
        TwentyFive(1500000),
        Fifty(3000000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.startTimerButton.setOnClickListener {
            val dialogBinding = DialogSetTimerBinding.inflate(layoutInflater)
            val dialogView = dialogBinding.root
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.show()

            dialogBinding.twentyFiveMinutesChip.setOnClickListener {
                startTimer(Time.TwentyFive.milliseconds)
                bottomSheetDialog.dismiss()
            }

            dialogBinding.fiftyMinutesChip.setOnClickListener {
                startTimer(Time.Fifty.milliseconds)
                bottomSheetDialog.dismiss()
            }
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

    private fun startTimer(milliseconds: Long) {
        updateStartButton(false, DISABLE_BUTTON_ALPHA)
        updateStopButton(true, ENABLE_BUTTON_ALPHA)

        binding.circularProgressBar.progressMax = (milliseconds / 1000).toFloat()
        timer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateUI(millisUntilFinished)
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    private fun updateUI(milliseconds: Long) {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)

        binding.circularProgressBar.apply {
            setProgressWithAnimation(progress + 1, 500)
        }
    }

    private fun updateStartButton(isClickable: Boolean, alpha: Float) {
        binding.startTimerButton.apply {
            this.isClickable = isClickable
            this.alpha = alpha
        }
    }

    private fun updateStopButton(isClickable: Boolean, alpha: Float) {
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