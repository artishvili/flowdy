package com.devshish.internship.data.api.genius

import com.devshish.internship.data.model.dto.SongDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GeniusSearchApi {

    @GET("search")
    suspend fun searchSongs(
        @Query("q") query: String
    ): SongDTO
}
