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

        binding.firstSongTitle.text = playerViewModel.getTitleSong(0)


        binding.secondSongTitle.text = playerViewModel.getTitleSong(1)

        binding.thirdSongTitle.text = playerViewModel.getTitleSong(2)


        binding.firstSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(0)

            if(playerViewModel.getPlayButtonPressed()){

            } else {
                binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())
            }



            Toast.makeText(this, "Your change first song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.secondSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(1)


            if(playerViewModel.getPlayButtonPressed()){

            } else {
                binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())
            }

           // binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())

            Toast.makeText(this, "Your change second song press Play button play music", Toast.LENGTH_LONG).show()
        }

        binding.thirdSongTitle.setOnClickListener {
            playerViewModel.setNumberSong(2)


            if(playerViewModel.getPlayButtonPressed()){

            } else {
                binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())
            }

          //  binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())

            Toast.makeText(this, "Your change third song press Play button play music", Toast.LENGTH_LONG).show()
        }

       // binding.currentNameSong.text = listSong[0].title
       binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())

        binding.leftArrowButton.setOnClickListener {
            var newNumber = playerViewModel.getNumberSong()  - 1

            if(newNumber < 0) {
                newNumber = listSong.size - 1
            }
            playerViewModel.setNumberSong(newNumber)

            binding.currentNameSong.text = playerViewModel.getTitleSong(newNumber)

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
            binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())
            val intentForBroadcastPlay = Intent(PLAY_ACTION).putExtra(NUMBER_TRACK, playerViewModel.getNumberSong())
            sendBroadcast(intentForBroadcastPlay)
        }

        binding.stopButton.setOnClickListener {
            playerViewModel.setPlayButtonPressed(playButtonPressed = false)
            binding.currentNameSong.text = playerViewModel.getTitleSong(playerViewModel.getNumberSong())
            val intentForBroadcastStop = Intent(STOP_ACTION)
            sendBroadcast(intentForBroadcastStop)

        }

        binding.rightArrowButton.setOnClickListener {
            //  playerService.playNext()
            var newNumber = playerViewModel.getNumberSong()  + 1
            newNumber %= listSong.size
            playerViewModel.setNumberSong(newNumber)
            binding.currentNameSong.text = playerViewModel.getTitleSong(newNumber)
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