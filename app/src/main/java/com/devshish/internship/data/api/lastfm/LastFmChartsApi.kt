package com.devshish.internship.data.api.lastfm

import com.devshish.internship.data.model.dto.ArtistListDTO
import com.devshish.internship.data.model.dto.TrackListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmChartsApi {

    companion object {
        private const val METHOD_GET_TOP_ARTISTS = "chart.gettopartists"
        private const val METHOD_GET_TOP_TRACKS = "chart.gettoptracks"
    }

    @GET(".")
    suspend fun getTopArtists(
        @Query("method") method: String = METHOD_GET_TOP_ARTISTS
    ): ArtistListDTO

    @GET(".")
    suspend fun getTopTracks(
        @Query("method") method: String = METHOD_GET_TOP_TRACKS
    ): TrackListDTO
}
