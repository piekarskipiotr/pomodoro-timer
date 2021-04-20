package com.apps.bacon.pomodorotimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.apps.bacon.pomodorotimer.databinding.ActivityHomeBinding
import com.apps.bacon.pomodorotimer.databinding.DialogCompletedSessionBinding
import com.apps.bacon.pomodorotimer.databinding.DialogSetTimerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var timer: CountDownTimer
    private lateinit var sharedPreference: SharedPreferences
    private var msUntilFinished: Long = 0

    enum class Time(val milliseconds: Long) {
        TwentyFive(1500000),
        Fifty(3000000),
        ShortBreak(300000),
        LongBreak(1800000)
    }

    enum class TimerState {
        RUNNING,
        IN_THE_BACKGROUND,
        FINISHED_IN_THE_BACKGROUND,
        STOPPED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreference = this.getSharedPreferences("TIMER_INFO", Context.MODE_PRIVATE)
        createNotificationChannel()

        binding.startTimerButton.setOnClickListener {
            val dialogBinding = DialogSetTimerBinding.inflate(layoutInflater)
            val dialogView = dialogBinding.root
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.show()

            dialogBinding.twentyFiveMinutesChip.setOnClickListener {
                startTimer(Time.TwentyFive.milliseconds)
                setSessionInfo(Time.TwentyFive.milliseconds)
                bottomSheetDialog.dismiss()
            }

            dialogBinding.fiftyMinutesChip.setOnClickListener {
                startTimer(Time.Fifty.milliseconds)
                setSessionInfo(Time.Fifty.milliseconds)
                bottomSheetDialog.dismiss()
            }
        }

        binding.stopTimerButton.setOnClickListener {
            timer.cancel()
            resetUI()
        }

        binding.userProfileButton.setOnClickListener {
            intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        updateStopButton(false, DISABLE_BUTTON_ALPHA)
    }

    private fun setSessionInfo(sessionTime: Long) {
        with(sharedPreference.edit()) {
            putLong(SESSION_TIME_KEY, sessionTime)
            putInt(SESSION_COUNT_KEY, 1)
            putBoolean(IS_BREAK_KEY, false)
            apply()
        }
    }

    private fun startTimer(milliseconds: Long) {
        updateStartButton(false, DISABLE_BUTTON_ALPHA)
        updateStopButton(true, ENABLE_BUTTON_ALPHA)
        with(sharedPreference.edit()) {
            putString(TIMER_STATE_KEY, TimerState.RUNNING.name)
            apply()
        }

        binding.circularProgressBar.progressMax = (milliseconds / 1000).toFloat()
        timer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                msUntilFinished = millisUntilFinished
                updateUI(millisUntilFinished)
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    private fun onTimerFinished() {
        resetUI()
        val timerForBreak = sharedPreference.getBoolean(IS_BREAK_KEY, false)

        if (timerForBreak)
            showBreakDialog()
        else
            showCompletedSessionDialog()

        val currentTimerState = sharedPreference.getString(TIMER_STATE_KEY, TimerState.STOPPED.name)
        var timerState = TimerState.STOPPED.name
        if (currentTimerState == TimerState.IN_THE_BACKGROUND.name) {
            timerState = TimerState.FINISHED_IN_THE_BACKGROUND.name

            if (timerForBreak)
                sendNotification(
                    getString(R.string.break_notify_title),
                    getString(R.string.break_notify_description)
                )
            else
                sendNotification(
                    getString(R.string.session_notify_title),
                    getString(R.string.session_notify_description)
                )
        }

        with(sharedPreference.edit()) {
            putString(TIMER_STATE_KEY, timerState)
            apply()
        }
    }

    private fun updateUI(milliseconds: Long) {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)

        binding.circularProgressBar.apply {
            setProgressWithAnimation(progress + 1, 500)
        }
    }

    private fun resetUI() {
        updateStopButton(false, DISABLE_BUTTON_ALPHA)
        updateStartButton(true, ENABLE_BUTTON_ALPHA)
        binding.timerText.text = getString(R.string.time_zero)
        binding.circularProgressBar.apply {
            setProgressWithAnimation(0f, 500)
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

    private fun showCompletedSessionDialog() {
        val alertDialog: AlertDialog
        val builder = AlertDialog.Builder(this, R.style.DialogStyle)
        val dialogBinding = DialogCompletedSessionBinding.inflate(LayoutInflater.from(this))
        val view = dialogBinding.root
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)

        with(sharedPreference.edit()) {
            putString(TIMER_STATE_KEY, TimerState.STOPPED.name)
            apply()
        }

        val sessionCount = sharedPreference.getInt(SESSION_COUNT_KEY, 1)
        var breakTime = Time.ShortBreak.milliseconds

        if (sessionCount % 4 == 0) {
            breakTime = Time.LongBreak.milliseconds
            dialogBinding.breakButtonText.text = getString(R.string.take_long_break)
        }

        dialogBinding.skipBreakButton.setOnClickListener {
            val sessionTime = sharedPreference.getLong(SESSION_TIME_KEY, 0L)
            with(sharedPreference.edit()) {
                putInt(SESSION_COUNT_KEY, sessionCount.inc())
                apply()
            }
            startTimer(sessionTime)
            alertDialog.dismiss()
        }

        dialogBinding.takeBreakButton.setOnClickListener {
            with(sharedPreference.edit()) {
                putInt(SESSION_COUNT_KEY, sessionCount.inc())
                putBoolean(IS_BREAK_KEY, true)
                apply()
            }
            startTimer(breakTime)
            alertDialog.dismiss()
        }

        dialogBinding.finishButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.RED
                enableLights(true)
            }

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(title: String, description: String) {
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_android)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0, notification)
    }

    override fun onBackPressed() {
        //no possibility of going back
    }

    override fun onPause() {
        super.onPause()
        val timerState = sharedPreference.getString(TIMER_STATE_KEY, TimerState.STOPPED.name)
        if (timerState == TimerState.RUNNING.name) {
            with(sharedPreference.edit()) {
                putString(TIMER_STATE_KEY, TimerState.IN_THE_BACKGROUND.name)
                apply()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val timerState = sharedPreference.getString(TIMER_STATE_KEY, TimerState.STOPPED.name)
        if (timerState == TimerState.IN_THE_BACKGROUND.name) {
            with(sharedPreference.edit()) {
                putString(TIMER_STATE_KEY, TimerState.RUNNING.name)
                apply()
            }
        } else if (timerState == TimerState.FINISHED_IN_THE_BACKGROUND.name) {
            val timerForBreak = sharedPreference.getBoolean(IS_BREAK_KEY, false)

            if (timerForBreak)
                showBreakDialog()
            else
                showCompletedSessionDialog()
        }
    }

    companion object {
        private const val DISABLE_BUTTON_ALPHA = 0.9f
        private const val ENABLE_BUTTON_ALPHA = 1f
        private const val CHANNEL_ID = "CHANNEL_P1"
        private const val CHANNEL_NAME = "POMODORO_TIMER_CHANNEL"

        //sharedPreference keys
        private const val TIMER_STATE_KEY = "TIMER_STATE"
        private const val SESSION_TIME_KEY = "SESSION_TIME"
        private const val SESSION_COUNT_KEY = "SESSION_COUNT"
        private const val IS_BREAK_KEY = "IS_BREAK"

    }
}