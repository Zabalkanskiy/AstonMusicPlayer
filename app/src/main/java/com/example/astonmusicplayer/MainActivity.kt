package com.example.astonmusicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.astonmusicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModelFactory = MyViewModelFactory(PlayerRepository())
        val playerViewModel = ViewModelProvider(this, viewModelFactory).get(PlayerViewModel::class.java)


       val newServiceIntent = Intent(this, PlayerService::class.java)
        ContextCompat.startForegroundService(this,newServiceIntent)

        binding.firstSongTitle.text = listSong[0].title


        binding.secondSongTitle.text = listSong[1].title

        binding.thirdSongTitle.text = listSong[2].title


        binding.firstSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(0)

            Toast.makeText(this, "Your change first song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.secondSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(1)
            Toast.makeText(this, "Your change second song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.thirdSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(2)
            Toast.makeText(this, "Your change third song press Play button play music", Toast.LENGTH_LONG).show()
        }


        binding.leftArrowButton.setOnClickListener {
            val intentForBroadcasPrevious = Intent(PREVIOUS_TRACK_ACTION)
            sendBroadcast(intentForBroadcasPrevious)

        }

        binding.playButton.setOnClickListener {

            val intentForBroadcastPlay = Intent(PLAY_ACTION).putExtra(NUMBER_TRACK, playerViewModel.getNumberSong())
            sendBroadcast(intentForBroadcastPlay)
        }

        binding.stopButton.setOnClickListener {
            //  playerService.pausePlayer()
            val intentForBroadcastStop = Intent(STOP_ACTION)
            sendBroadcast(intentForBroadcastStop)

        }

        binding.rightArrowButton.setOnClickListener {
            //  playerService.playNext()

            val intentForBroadcastNext = Intent(NEXT_TRACK_ACTION)
            sendBroadcast(intentForBroadcastNext)

        }


    }


}