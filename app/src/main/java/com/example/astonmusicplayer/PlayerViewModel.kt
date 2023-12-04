package com.example.astonmusicplayer

import androidx.lifecycle.ViewModel

class PlayerViewModel(playerRepository: PlayerRepository) : ViewModel(){
      private  val playerRepositoryinModel = playerRepository
    fun setNumberSong(numberSong: Int){
        playerRepositoryinModel.numberSong = numberSong

    }

    fun getNumberSong(): Int = playerRepositoryinModel.numberSong

    fun getPlayButtonPressed() : Boolean = playerRepositoryinModel.playButtonPressed

    fun setPlayButtonPressed( playButtonPressed: Boolean){
        playerRepositoryinModel.playButtonPressed = playButtonPressed
    }

    fun getTitleSong(indexSong: Int): String{
        return    playerRepositoryinModel.listSongs[indexSong].title
    }
}