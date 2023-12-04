package com.example.astonmusicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(private val playerRepository: PlayerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            return PlayerViewModel(playerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}