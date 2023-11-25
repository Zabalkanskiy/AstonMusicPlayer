package com.example.astonmusicplayer

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class PlayerService : Service() {






    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

const val CHANNEL_ID = "FOREGROUND_SERVICE_CHANNEL"

const val PLAY_ACTION = "PLAY_MUSIC_ACTION"
const val PAUSE_ACTION = "PAUSE_ACTION"
const val STOP_ACTION = "STOP_ACTION"
const val NEXT_TRACK_ACTION = "NEXT_TRACK_ACTION"
const val PREVIOUS_TRACK_ACTION = "PREVIOUS_TRACK_ACTION"
const val NUMBER_TRACK = "NUMBER_TRACK"