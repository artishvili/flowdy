package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Artist

interface IArtistRepository {

    suspend fun getTopArtists(): List<Artist>
}
