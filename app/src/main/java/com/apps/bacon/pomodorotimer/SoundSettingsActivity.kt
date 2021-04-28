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

    private fun initRecordingDialog() {

    }

    private fun startTimer() {
        startRecording()
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
        private const val REQUEST_CODE_PERMISSIONS = 7
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )
    }
}