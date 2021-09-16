package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.presentation.ui.utils.Constants.UPDATE_PLAYER_POSITION_INTERVAL
import com.devshish.internship.presentation.ui.utils.currentPlaybackPosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val playbackState = musicServiceConnection.playbackState

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration: LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition: LiveData<Long> = _curPlayerPosition

    init {
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while (true) {
                val position = playbackState.value?.currentPlaybackPosition
                if (curPlayerPosition.value != position) {
                    _curPlayerPosition.postValue(position!!)
                    _curSongDuration.postValue(MusicService.curSongDuration)
                }
                delay(UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }
}
