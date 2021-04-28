package com.apps.bacon.pomodorotimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.apps.bacon.pomodorotimer.databinding.ActivityTimeSettingsBinding
import com.apps.bacon.pomodorotimer.viewmodel.TimeSettingsViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeSettingsBinding
    private lateinit var currentMinutes: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val timeSettingViewModel: TimeSettingsViewModel by viewModels()

        timeSettingViewModel.getCustomTimeOfSession().observe(this, {
            currentMinutes = (it / MINUTES_TO_MILLISECONDS_CONVERTER).toString()
            binding.currentTimeText.text =
                "${getString(R.string.current_custom_time)} $currentMinutes"
        })

        binding.customTimeTextInput.onTextChanged {
            if (it.isNullOrEmpty())
                setMessage(getString(R.string.time_error_empty), true)
            else {
                when {
                    it.toString() == currentMinutes -> setMessage(
                        getString(R.string.time_error_same),
                        true
                    )
                    it[0] == '0' -> setMessage(
                        getString(R.string.time_error_value_starts_with_0),
                        true
                    )
                    it.toString().toInt() > 60 -> setMessage(
                        getString(R.string.time_error_higher),
                        true
                    )
                    it.contains(Regex("[^0-9]")) -> setMessage(
                        getString(R.string.time_error_chars),
                        true
                    )
                    else -> setMessage(getString(R.string.timer_error_ok), false)
                }
            }
        }

        binding.doneButton.setOnClickListener {
            val newCustomTime = binding.customTimeTextInput.text.toString()
                .toLong() * MINUTES_TO_MILLISECONDS_CONVERTER
            timeSettingViewModel.updateCustomTimeOfSession(newCustomTime)
            setMessage("", true)
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        }

        binding.returnButton.setOnClickListener {
            onBackPressed()
        }

        binding.doneButton.isClickable = false
    }

    private fun setMessage(message: String, isErrorMessage: Boolean) {
        binding.messageText.text = message
        if (isErrorMessage) {
            binding.doneButton.isClickable = false
            binding.doneButton.alpha = 0.8f
        } else {
            binding.doneButton.isClickable = true
            binding.doneButton.alpha = 1.0f
        }
    }

    private fun TextInputEditText.onTextChanged(onTextChanged: (CharSequence?) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChanged.invoke(p0)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    companion object {
        private const val MINUTES_TO_MILLISECONDS_CONVERTER = 60000L
    }
}