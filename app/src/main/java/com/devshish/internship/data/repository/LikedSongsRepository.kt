package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.Song
import com.devshish.internship.domain.repository.ISongsRepository

class LikedSongsRepository : ISongsRepository {

    private val songs = listOf(
        Song(null, "Tell Em", "Cochise, SNOT", 300000, null),
        Song(null, "Spaceship", "Playboi Carti", 300000, null),
        Song(null, "Water Glass", "Cannon", 300000, null),
        Song(null, "Ms. Jackson", "Outkast", 300000, null),
        Song(null, "Ninety", "Jaden", 300000, null),
        Song(null, "Dunno", "Mac Miller", 300000, null),
        Song(null, "DR. WHOEVER", "Amine", 300000, null),
        Song(null, "Well Travelled", "Masego", 300000, null),
        Song(null, "Been Around", "Cordae", 300000, null),
        Song(null, "Trust", "Brent Faiyaz", 300000, null),
        Song(null, "YEAH RIGHT", "Joji", 300000, null),
        Song(null, "RIVER ROAD", "Jack Harlow", 300000, null)
    )

    override suspend fun getSongs(): List<Song> = songs
}
