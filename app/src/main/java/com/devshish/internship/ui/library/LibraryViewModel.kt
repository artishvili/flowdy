package com.devshish.internship.ui.library

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.model.Song
import timber.log.Timber

class LibraryViewModel : ViewModel() {

    val songs: LiveData<List<Song>>
        get() = _songs
    private val _songs = MutableLiveData<List<Song>>()

    init {
        _songs.value = listOf(
            Song("Tell Em", "Cochise, SNOT", 260, ""),
            Song("Spaceship", "Playboi Carti", 300, ""),
            Song("Water Glass", "Cannon", 240, ""),
            Song("Ms. Jackson", "Outkast", 240, ""),
            Song("Ninety", "Jaden", 240, ""),
            Song("Dunno", "Mac Miller", 240, ""),
            Song("DR. WHOEVER", "Amine", 240, ""),
            Song("Well Travelled", "Masego", 240, ""),
            Song("Been Around", "Cordae", 240, ""),
            Song("Trust", "Brent Faiyaz", 240, ""),
            Song("YEAH RIGHT", "Joji", 240, ""),
            Song("RIVER ROAD", "Jack Harlow", 240, "")
        )
    }

    private val TAG = "song_clicked"
    fun songClicked(song: Song) {
        Timber.d("Song clicked: $song")
    }
}
