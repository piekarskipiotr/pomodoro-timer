package com.apps.bacon.pomodorotimer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.apps.bacon.pomodorotimer.util.DateService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = this.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val isFirstRun = sharedPreference.getBoolean(FIRST_RUN_KEY, true)
            intent = if (isFirstRun)
                Intent(this, HelloActivity::class.java)
            else {
                val dateServiceIntent = Intent(this, DateService::class.java)
                startService(dateServiceIntent)
                Intent(this, HomeActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, DELAY)
    }

    companion object {
        const val DELAY: Long = 1000

        //sharedPreference keys
        const val APP_PREFERENCES_KEY = "APP_PREFERENCES"
        const val FIRST_RUN_KEY = "FIRST_RUN"
    }
}