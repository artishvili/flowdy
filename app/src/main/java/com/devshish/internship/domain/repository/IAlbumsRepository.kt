package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Album

interface IAlbumsRepository {

    suspend fun getAlbums(): List<Album>
}
