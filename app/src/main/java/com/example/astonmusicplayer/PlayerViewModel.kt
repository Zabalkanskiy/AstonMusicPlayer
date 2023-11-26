package com.example.astonmusicplayer

import androidx.lifecycle.ViewModel

class PlayerViewModel(playerRepository: PlayerRepository) : ViewModel(){
      private  val playerRepositoryinModel = playerRepository
    fun setNumberSong(numberSong: Int){
        playerRepositoryinModel.numberSong = numberSong

    }

    fun getNumberSong(): Int = playerRepositoryinModel.numberSong


}