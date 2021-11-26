package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.Artist
import com.devshish.internship.domain.model.Track

interface IChartsRepository {

    suspend fun getTopArtists(): List<Artist>

    suspend fun getTopTracks(): List<Track>
}
