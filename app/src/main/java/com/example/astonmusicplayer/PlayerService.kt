package com.example.astonmusicplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class PlayerService : Service() {

    private lateinit var player: MediaPlayer

    private var songs: List<Song> = listSong

    //current position
    private var songPosn = 0

    private val serviceBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val actionString = intent.action
            when (actionString){
                PLAY_ACTION ->{
                    val numberTrack = intent.getIntExtra(NUMBER_TRACK, 0)
                    songPosn = numberTrack
                    playMusic(numberTrack)

                }
                STOP_ACTION ->{
                    stopMusic()
                }
              //  PAUSE_ACTION ->{
//

              //  }
                NEXT_TRACK_ACTION ->{
                    val numberTrack = intent.getIntExtra(NUMBER_TRACK, 0)
                   // numberTrack = numberTrack  % listSong.size
                    stopMusic()
                    playMusic(numberTrack)

                }
                PREVIOUS_TRACK_ACTION -> {
                    val numberTrack = intent.getIntExtra(NUMBER_TRACK, 0)

                        stopMusic()
                        playMusic(numberTrack)


                }
            }

        }
    }

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter()
        intentFilter.addAction(PLAY_ACTION)
        intentFilter.addAction(STOP_ACTION)
        intentFilter.addAction(PAUSE_ACTION)
        intentFilter.addAction(NEXT_TRACK_ACTION)
        intentFilter.addAction(PREVIOUS_TRACK_ACTION)

        ContextCompat.registerReceiver(this, serviceBroadcastReceiver, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED)


        // create Media Player

        player = MediaPlayer()

        player.apply {
            setWakeMode(
                applicationContext,
                PowerManager.PARTIAL_WAKE_LOCK
            )
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()

            )
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // custom notification view
        //  val notificationLayout = RemoteViews(packageName, com.seven.astonplayer.R.layout.custom_notification)
        //   notificationLayout.setImageViewResource(com.seven.astonplayer.R.id.notification_play_button,)

        val input = intent.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            //  .setOngoing(true)
            .setSilent(true)
            .setContentTitle("Foreground Music Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.my_play_button)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(serviceBroadcastReceiver)
        super.onDestroy()
    }

    fun playMusic(numberTrack : Int){
        //  val mediaPlayer: MediaPlayer = MediaPlayer.create(this, com.seven.astonplayer.R.raw.voin)
        //   mediaPlayer.start()
        player.reset()

        val songRawID = songs[numberTrack].resID

        val afd: AssetFileDescriptor = applicationContext.getResources().openRawResourceFd(songRawID)

        try {
            //  player.setDataSource(applicationContext, playSong.resID)
            //Возможно неправильно
            player.setDataSource(afd)
            afd.close()
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error setting data source", e)
        }
        player.setOnPreparedListener {
            it.start()
        }
        player.setOnCompletionListener {
            val intentForBroadcastNext = Intent(NEXT_TRACK_ACTION)
            sendBroadcast(intentForBroadcastNext)
        }
        player.prepareAsync()

    }

    fun stopMusic(){

        player.stop()


    }



    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
}

const val CHANNEL_ID = "FOREGROUND_SERVICE_CHANNEL"

const val PLAY_ACTION = "PLAY_MUSIC_ACTION"
const val PAUSE_ACTION = "PAUSE_ACTION"
const val STOP_ACTION = "STOP_ACTION"
const val NEXT_TRACK_ACTION = "NEXT_TRACK_ACTION"
const val PREVIOUS_TRACK_ACTION = "PREVIOUS_TRACK_ACTION"
const val NUMBER_TRACK = "NUMBER_TRACK"