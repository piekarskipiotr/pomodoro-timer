package com.apps.bacon.pomodorotimer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class SoundService : Service() {
    private lateinit var player: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        initMediaPlayer()
        player.start()
        player.setOnCompletionListener {
            this.stopSelf()
        }

        return START_STICKY
    }

    private fun initMediaPlayer(){
        //attached sound file is for testing and in the future it will be replaced
        player = MediaPlayer.create(this, R.raw.yourefinallyawake).apply {
            isLooping = false
            setVolume(100f, 100f)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }
}
