package com.apps.bacon.pomodorotimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = this.getSharedPreferences(
            "USER_INFO",
            Context.MODE_PRIVATE
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val isFirstRun = sharedPreference.getBoolean("FIRST_RUN", true)
            intent = if (isFirstRun)
                Intent(this, HelloActivity::class.java)
            else
                Intent(this, HomeActivity::class.java)

            startActivity(intent)
            finish()

        }, DELAY)
    }

    companion object{
        const val DELAY: Long = 1000
    }
}