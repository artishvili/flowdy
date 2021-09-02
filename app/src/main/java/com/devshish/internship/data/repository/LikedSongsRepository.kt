package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository

class LikedSongsRepository : ISongsRepository {

    private val songs = listOf(
        Song(null, "Tell Em", "Cochise, SNOT", 300000L, ""),
        Song(null, "Spaceship", "Playboi Carti", 300000L, ""),
        Song(null, "Water Glass", "Cannon", 300000L, ""),
        Song(null, "Ms. Jackson", "Outkast", 300000L, ""),
        Song(null, "Ninety", "Jaden", 300000L, ""),
        Song(null, "Dunno", "Mac Miller", 300000L, ""),
        Song(null, "DR. WHOEVER", "Amine", 300000L, ""),
        Song(null, "Well Travelled", "Masego", 300000L, ""),
        Song(null, "Been Around", "Cordae", 300000L, ""),
        Song(null, "Trust", "Brent Faiyaz", 300000L, ""),
        Song(null, "YEAH RIGHT", "Joji", 300000L, ""),
        Song(null, "RIVER ROAD", "Jack Harlow", 300000L, "")
    )

    override suspend fun getSongs(): List<Song> = songs
}
