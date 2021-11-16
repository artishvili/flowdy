package com.devshish.internship.data.repository

import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.lastfm.LastFmChartsApi
import com.devshish.internship.domain.model.Artist
import com.devshish.internship.domain.repository.IArtistRepository

class ArtistRepository(
    private val api: LastFmChartsApi
) : IArtistRepository {

    companion object {
        private const val METHOD_GET_TOP_ARTISTS = "chart.gettopartists"
    }

    override suspend fun getTopArtists(): List<Artist> =
        api.getTopArtists(
            url = BuildConfig.LASTFM_BASE_URL,
            method = METHOD_GET_TOP_ARTISTS,
            apiKey = BuildConfig.LASTFM_API_KEY
        ).artists.artist.map { dto ->
            Artist(
                listeners = dto.listeners,
                name = dto.name,
                playcount = dto.playcount,
                streamable = dto.streamable,
                url = dto.url
            )
        }
}
