package com.apps.bacon.pomodorotimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }, DELAY)
    }

    companion object{
        const val DELAY: Long = 1000
    }
}