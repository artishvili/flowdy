package com.devshish.internship.presentation

import com.devshish.internship.domain.models.Song

class SongsRepository : LikedSongsRepository {

    private val songs = listOf(
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

    override suspend fun getLikedSongs() = songs
}