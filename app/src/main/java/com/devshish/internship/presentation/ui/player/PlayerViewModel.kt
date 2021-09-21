package com.devshish.internship.presentation.ui.player

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.Constants.MEDIA_ROOT_ID
import com.devshish.internship.presentation.ui.utils.Constants.UPDATE_PLAYER_POSITION_INTERVAL
import com.devshish.internship.presentation.ui.utils.currentPlaybackPosition
import com.devshish.internship.presentation.ui.utils.isPlayEnabled
import com.devshish.internship.presentation.ui.utils.isPlaying
import com.devshish.internship.presentation.ui.utils.isPrepared
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val serviceConnection: MusicServiceConnection
) : ViewModel() {

    val isPlaying: LiveData<Boolean>
        get() = _isPlaying
    private val _isPlaying = MutableLiveData<Boolean>()

    val playbackState = serviceConnection.playbackState
    val currentSong = serviceConnection.curPlayingSong

//    var callback: (() -> Any)? = null

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration: LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition: LiveData<Long> = _curPlayerPosition

    init {
        _isPlaying.value = serviceConnection.isPlaying?.let {  }

        /*serviceConnection.subscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {

                    }
                }
            }
        )*/

        updateCurrentPlayerPosition()
    }

    fun toggle(song: Song, stop: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && song.mediaId == currentSong.value?.getString(METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { state ->
                when {
                    state.isPlaying -> if (stop) serviceConnection.transportControls.pause()
                    state.isPlayEnabled -> serviceConnection.transportControls.play()
                }
            }
        } else {
            serviceConnection.transportControls.playFromMediaId(song.mediaId, null)
        }
    }

    fun seekTo(position: Int) {
        serviceConnection.transportControls.seekTo(position.toLong())
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

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unsubscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {}
        )
    }
}
