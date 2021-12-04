package com.devshish.internship.presentation.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISearchSongsRepository
import com.devshish.internship.presentation.service.player.client.MediaBrowserClient
import com.devshish.internship.presentation.ui.utils.Event
import com.devshish.internship.presentation.ui.utils.convertMillisToTime
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val mediaBrowser: MediaBrowserClient,
    private val repository: ISearchSongsRepository
) : ViewModel() {

    val songToPlay: LiveData<Song>
        get() = _songToPlay
    private val _songToPlay = MutableLiveData<Song>()

    val isPlaying: LiveData<Boolean>
        get() = _isPlaying
    private val _isPlaying = MutableLiveData<Boolean>()

    val currentPosition: LiveData<Int>
        get() = _currentPosition
    private val _currentPosition = MutableLiveData<Int>()

    val playbackPosition: LiveData<String>
        get() = _playbackPosition
    private val _playbackPosition = MutableLiveData<String>()

    val isPlayerBarVisible: LiveData<Boolean>
        get() = _isPlayerBarVisible
    private val _isPlayerBarVisible = MutableLiveData<Boolean>()

    val isLyricsButtonVisible: LiveData<Boolean>
        get() = _isLyricsButtonVisible
    private val _isLyricsButtonVisible = MutableLiveData<Boolean>()

    val navigateToLyricsEvent: LiveData<Event<SearchSong>>
        get() = _navigateToLyricsEvent
    private val _navigateToLyricsEvent = MutableLiveData<Event<SearchSong>>()

    private val searchSong = MutableLiveData<SearchSong>()

    init {
        mediaBrowser.currentSongCallback = object : MediaBrowserClient.CurrentSongCallback {
            override fun updateSong(song: Song) {
                _songToPlay.value = song
                _isPlayerBarVisible.value = true

                viewModelScope.launch {
                    searchSong.value = repository.searchSongs(song.title + song.artist)
                        .findConcreteSong(song)
                    _isLyricsButtonVisible.value = searchSong.value != null
                }
            }

            override fun getState(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun getPosition(position: Long) {
                _songToPlay.value?.duration?.let {
                    if (it > position.toInt()) {
                        _currentPosition.value = position.toInt()
                    } else {
                        _currentPosition.value = _songToPlay.value?.duration
                    }
                }
            }

            override fun getPlayerBarVisibility(isVisible: Boolean) {
                _isPlayerBarVisible.value = isVisible
            }
        }
    }

    private fun List<SearchSong>.findConcreteSong(song: Song): SearchSong? {
        return firstOrNull {
            it.title.contains(song.title) && it.artist.contains(song.artist)
        }
    }

    fun toggle() {
        mediaBrowser.toggle()
    }

    fun seekTo(position: Long) {
        mediaBrowser.seekTo(position)
    }

    fun onProgressChanged(progress: Int) {
        _playbackPosition.value = convertMillisToTime(progress)
    }

    fun onLyricsClick() {
        _navigateToLyricsEvent.value = Event(searchSong.value ?: return)
    }

    override fun onCleared() {
        super.onCleared()
        mediaBrowser.currentSongCallback = null
    }
}
