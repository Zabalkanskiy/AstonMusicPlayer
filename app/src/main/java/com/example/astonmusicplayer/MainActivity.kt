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
   // var playButtonPressed = false
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

            binding.currentNameSong.text = listSong[0].title

            Toast.makeText(this, "Your change first song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.secondSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(1)

            binding.currentNameSong.text = listSong[1].title

            Toast.makeText(this, "Your change second song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.thirdSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(2)

            binding.currentNameSong.text = listSong[2].title

            Toast.makeText(this, "Your change third song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.currentNameSong.text = listSong[0].title


        binding.leftArrowButton.setOnClickListener {
            var newNumber = playerViewModel.getNumberSong()  - 1

            if(newNumber < 0) {
                newNumber = listSong.size - 1
            }
            playerViewModel.setNumberSong(newNumber)

            binding.currentNameSong.text = listSong[newNumber].title

            if(playerViewModel.getPlayButtonPressed()) {
                val intentForBroadcasPrevious = Intent(PREVIOUS_TRACK_ACTION).putExtra(
                    NUMBER_TRACK,
                    playerViewModel.getNumberSong()
                )
                sendBroadcast(intentForBroadcasPrevious)
            }
        }

        binding.playButton.setOnClickListener {
            playerViewModel.setPlayButtonPressed(playButtonPressed = true)

            val intentForBroadcastPlay = Intent(PLAY_ACTION).putExtra(NUMBER_TRACK, playerViewModel.getNumberSong())
            sendBroadcast(intentForBroadcastPlay)
        }

        binding.stopButton.setOnClickListener {
            playerViewModel.setPlayButtonPressed(playButtonPressed = false)
            val intentForBroadcastStop = Intent(STOP_ACTION)
            sendBroadcast(intentForBroadcastStop)

        }

        binding.rightArrowButton.setOnClickListener {
            //  playerService.playNext()
            var newNumber = playerViewModel.getNumberSong()  + 1
            newNumber %= listSong.size
            playerViewModel.setNumberSong(newNumber)
            binding.currentNameSong.text = listSong[newNumber].title
            if (playerViewModel.getPlayButtonPressed()) {
                val intentForBroadcastNext = Intent(NEXT_TRACK_ACTION).putExtra(
                    NUMBER_TRACK,
                    playerViewModel.getNumberSong()
                )
                sendBroadcast(intentForBroadcastNext)
            }

        }


    }


}