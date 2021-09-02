package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.LikedSongsRepository

class SongsRepository : LikedSongsRepository {

    private val songs = listOf(
        Song(null, "Tell Em", "Cochise, SNOT", 260, ""),
        Song(null, "Spaceship", "Playboi Carti", 300, ""),
        Song(null, "Water Glass", "Cannon", 240, ""),
        Song(null, "Ms. Jackson", "Outkast", 240, ""),
        Song(null, "Ninety", "Jaden", 240, ""),
        Song(null, "Dunno", "Mac Miller", 240, ""),
        Song(null, "DR. WHOEVER", "Amine", 240, ""),
        Song(null, "Well Travelled", "Masego", 240, ""),
        Song(null, "Been Around", "Cordae", 240, ""),
        Song(null, "Trust", "Brent Faiyaz", 240, ""),
        Song(null, "YEAH RIGHT", "Joji", 240, ""),
        Song(null, "RIVER ROAD", "Jack Harlow", 240, "")
    )

    override suspend fun getLikedSongs(): List<Song> = songs
}