package com.apps.bacon.pomodorotimer.util

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import com.apps.bacon.pomodorotimer.data.repositories.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SoundService : Service() {
    @Inject
    lateinit var repository: UserRepository
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

    private fun initMediaPlayer() {
        val alarmSoundPath = repository.getAlarmSound()
        player = if (alarmSoundPath is String) {
            val uri = Uri.fromFile(File(alarmSoundPath))
            MediaPlayer.create(this, uri)
        } else {
            MediaPlayer.create(this, alarmSoundPath as Int)
        }.apply {
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
