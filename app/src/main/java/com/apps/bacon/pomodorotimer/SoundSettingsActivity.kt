package com.apps.bacon.pomodorotimer

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apps.bacon.pomodorotimer.databinding.ActivitySoundSettingsBinding
import com.apps.bacon.pomodorotimer.databinding.DialogRecordingBinding
import com.apps.bacon.pomodorotimer.viewmodel.SoundSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class SoundSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoundSettingsBinding
    private lateinit var dialogBinding: DialogRecordingBinding
    private val soundSettingsViewModel: SoundSettingsViewModel by viewModels()
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoundSettingsBinding.inflate(layoutInflater)
        dialogBinding = DialogRecordingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.alarmOneButton.setOnClickListener {
            setSelectedAlarmImage(View.VISIBLE, View.GONE, View.GONE)
            soundSettingsViewModel.updateSelectedAlarm(1)
        }

        binding.alarmTwoButton.setOnClickListener {
            setSelectedAlarmImage(View.GONE, View.VISIBLE, View.GONE)
            soundSettingsViewModel.updateSelectedAlarm(2)
        }

        binding.alarmOwnButton.setOnClickListener {
            if (allPermissionsGranted()) {
                initRecordingDialog()
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }

        binding.returnButton.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecordingDialog() {
        val alertDialog: AlertDialog
        val builder = AlertDialog.Builder(this, R.style.DialogStyle)
        val view = dialogBinding.root
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setOnDismissListener {
            dialogBinding.circularProgressBar.progress = 0f
            val viewGroup = view.parent as ViewGroup
            viewGroup.removeView(view)
        }

        dialogBinding.recordButton.setOnTouchListener(recordingTouchListener)

        dialogBinding.cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun startTimer() {
        dialogBinding.circularProgressBar.progressMax = (RECORDING_TIME / 1000).toFloat()
        timer = object : CountDownTimer(RECORDING_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                dialogBinding.circularProgressBar.apply {
                    setProgressWithAnimation(progress + 1f, 1000)
                }
            }

            override fun onFinish() {
                onTimerFinish()
            }
        }.start()
        startRecording()
    }

    private fun onTimerFinish() {
        stopRecording()
        soundSettingsViewModel.updateSelectedAlarm(3)
        setSelectedAlarmImage(View.GONE, View.GONE, View.VISIBLE)
        dialogBinding.circularProgressBar.apply {
            setProgressWithAnimation(0f, 500)
        }
    }

    private fun startRecording() {

    }

    private fun stopRecording() {

    }

    private fun setSelectedAlarmImage(oneVisibility: Int, twoVisibility: Int, ownVisibility: Int) {
        binding.alarmOneSelected.visibility = oneVisibility
        binding.alarmTwoSelected.visibility = twoVisibility
        binding.alarmOwnSelected.visibility = ownVisibility
    }

    @SuppressLint("ClickableViewAccessibility")
    private val recordingTouchListener = View.OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.isPressed = true
                startTimer()
            }

            MotionEvent.ACTION_UP -> {
                v.isPressed = false
                timer?.cancel()
                onTimerFinish()
            }
        }

        true
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                initRecordingDialog()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    companion object {
        private const val RECORDING_TIME = 10000L
        private const val REQUEST_CODE_PERMISSIONS = 7
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )
    }
}