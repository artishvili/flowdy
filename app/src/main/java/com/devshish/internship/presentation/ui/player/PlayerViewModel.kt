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

// TODO CHECK IF MEDIA BROWSER CALLBACK IS NULL TO SHOW/HIDE PLAYER BAR
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

    val isLyricsButtonVisible: LiveData<Boolean>
        get() = _isLyricsButtonVisible
    private val _isLyricsButtonVisible = MutableLiveData<Boolean>()

    private val searchSong: LiveData<SearchSong>
        get() = _searchSong
    private val _searchSong = MutableLiveData<SearchSong>()

    val navigateToLyricsEvent: LiveData<Event<SearchSong>>
        get() = _navigateToLyricsEvent
    private val _navigateToLyricsEvent = MutableLiveData<Event<SearchSong>>()

    init {
        mediaBrowser.currentSongCallback = object : MediaBrowserClient.CurrentSongCallback {
            override fun updateSong(song: Song) {
                _songToPlay.value = song

                // TODO HARDCODED LOGIC
                viewModelScope.launch {
                    _searchSong.value = repository.searchSongs(song.title).first()
                    _isLyricsButtonVisible.value = searchSong.value != null
                }
            }

            override fun getState(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun getPosition(position: Long) {
                _currentPosition.value = position.toInt()
            }
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
