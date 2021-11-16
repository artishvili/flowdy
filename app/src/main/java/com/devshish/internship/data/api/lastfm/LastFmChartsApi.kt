package com.devshish.internship.data.api.lastfm

import com.devshish.internship.data.model.dto.ArtistListDTO
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface LastFmChartsApi {

    @GET
    suspend fun getTopArtists(
        @Url url: String,
        @Query("method") method: String,
        @Query("api_key") apiKey: String
    ): ArtistListDTO
}
