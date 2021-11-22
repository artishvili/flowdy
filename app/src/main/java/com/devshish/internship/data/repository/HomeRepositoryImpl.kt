package com.devshish.internship.data.repository

import com.devshish.internship.data.api.lastfm.LastFmChartsApi
import com.devshish.internship.domain.model.Artist
import com.devshish.internship.domain.model.Track
import com.devshish.internship.domain.repository.IHomeRepository

class HomeRepositoryImpl(
    private val api: LastFmChartsApi
) : IHomeRepository {

    override suspend fun getTopArtists(): List<Artist> =
        api.getTopArtists().artists.artist.map { artist ->
            Artist(
                listeners = artist.listeners,
                name = artist.name,
                playCount = artist.playcount
            )
        }

    override suspend fun getTopTracks(): List<Track> =
        api.getTopTracks().tracks.track.map { track ->
            Track(
                name = track.name,
                artist = track.artist.name,
                listeners = track.listeners,
                playCount = track.playCount
            )
        }
}
