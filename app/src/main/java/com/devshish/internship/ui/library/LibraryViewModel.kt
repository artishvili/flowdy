package com.devshish.internship.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devshish.internship.model.Song

class LibraryViewModel : ViewModel() {

    private val songsList = listOf(
        Song("Tell Em", "Cochise, SNOT", 260),
        Song("Spaceship", "Playboi Carti", 300),
        Song("Water Glass", "Cannon", 240),
        Song("Ms. Jackson", "Outkast", 240),
        Song("Ninety", "Jaden", 240),
        Song("Dunno", "Mac Miller", 240),
        Song("DR. WHOEVER", "Amine", 240),
        Song("Well Travelled", "Masego", 240),
        Song("Been Around", "Cordae", 240),
        Song("Trust", "Brent Faiyaz", 240),
        Song("YEAH RIGHT", "Joji", 240),
        Song("RIVER ROAD", "Jack Harlow", 240)
    )

    val songs = MutableLiveData<List<Song>>().also {
        it.value = songsList
    }
}
