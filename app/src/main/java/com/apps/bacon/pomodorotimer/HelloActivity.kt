package com.apps.bacon.pomodorotimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.apps.bacon.pomodorotimer.databinding.ActivityHelloBinding
import com.google.android.material.textfield.TextInputEditText

class HelloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = getColor(R.color.light_blue)

        binding.userNameTextInput.onTextChanged {
            when{
                it.isNullOrEmpty() -> setMessage(getString(R.string.name_error_empty),true)
                it.length < 3 -> setMessage(getString(R.string.name_error_to_short),true)
                it.length > 18 -> setMessage(getString(R.string.name_error_to_long),true)
                it.contains(Regex("[^A-Za-z]")) -> setMessage(getString(R.string.name_error_wrong_chars), true)
                else -> setMessage(getString(R.string.name_ok),false)
            }
        }

        binding.finishButton.setOnClickListener {
            val sharedPreference = this.getSharedPreferences(
                "USER_INFO",
                Context.MODE_PRIVATE
            )
            val userName = binding.userNameTextInput.text.toString().trim()
            with(sharedPreference.edit()){
                putString("USER_NAME", userName)
                putBoolean("FIRST_RUN", false)
                apply()
            }

            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMessage(message: String, isErrorMessage: Boolean){
        binding.messageText.text = message
        if (isErrorMessage){
            binding.finishButton.isClickable = false
            binding.finishButton.alpha = 0.6f
        } else {
            binding.finishButton.isClickable = true
            binding.finishButton.alpha = 1.0f
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
}