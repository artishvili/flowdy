package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.Album
import com.devshish.internship.domain.repository.IAlbumsRepository

class LikedAlbumsRepository : IAlbumsRepository {

    private val albums = listOf(
        Album("Donda", "Kanye West", null),
        Album("Eternal Atake", "Lil Uzi Vert", null),
        Album("DAMN.", "Kendrick Lamar", null)
    )

    override suspend fun getAlbums(): List<Album> = albums
}
