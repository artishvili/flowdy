package com.devshish.internship.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshish.internship.R
import com.devshish.internship.domain.model.Artist
import com.devshish.internship.domain.model.Track
import com.devshish.internship.domain.repository.IChartsRepository
import com.devshish.internship.presentation.ui.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val repository: IChartsRepository
) : ViewModel() {

    val topArtists: LiveData<List<Artist>>
        get() = _topArtists
    private val _topArtists = MutableLiveData<List<Artist>>()

    val topTracks: LiveData<List<Track>>
        get() = _topTracks
    private val _topTracks = MutableLiveData<List<Track>>()

    val isLayoutRefreshing: LiveData<Boolean>
        get() = _isLayoutRefreshing
    private val _isLayoutRefreshing = MutableLiveData<Boolean>()

    val messageRes: LiveData<Event<Int>>
        get() = _messageRes
    private val _messageRes = MutableLiveData<Event<Int>>()

    val navigationEvent: LiveData<Event<String>>
        get() = _navigationEvent
    private val _navigationEvent = MutableLiveData<Event<String>>()

    init {
        refreshCharts()
    }

    fun refreshCharts() {
        viewModelScope.launch {
            try {
                _isLayoutRefreshing.value = true
                _topArtists.value = repository.getTopArtists()
                _topTracks.value = repository.getTopTracks()
            } catch (e: Exception) {
                _messageRes.value = Event(R.string.snackbar_something_went_wrong)
                Timber.e(e)
            } finally {
                _isLayoutRefreshing.value = false
            }
        }
    }

    fun onArtistOrTrackClick(url: String) {
        _navigationEvent.value = Event(url)
    }
}
