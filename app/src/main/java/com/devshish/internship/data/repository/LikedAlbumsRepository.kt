package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.Album
import com.devshish.internship.domain.repository.IAlbumsRepository

class LikedAlbumsRepository : IAlbumsRepository {

    private val albums = listOf(
        Album(null, "Donda", "Kanye West", null),
        Album(null, "Eternal Atake", "Lil Uzi Vert", null),
        Album(null, "DAMN.", "Kendrick Lamar", null)
    )

    override suspend fun getAlbums(): List<Album> = albums
}
