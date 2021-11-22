package com.devshish.internship.data.api.lastfm

import com.devshish.internship.data.model.dto.ArtistListDTO
import com.devshish.internship.data.model.dto.TrackListDTO
import retrofit2.http.GET

interface LastFmChartsApi {

    @GET("?method=chart.gettopartists")
    suspend fun getTopArtists(): ArtistListDTO

    @GET("?method=chart.gettoptracks")
    suspend fun getTopTracks(): TrackListDTO
}
