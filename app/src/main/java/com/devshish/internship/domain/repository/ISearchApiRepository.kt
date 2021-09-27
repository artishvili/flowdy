package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.searchDTO.SongDTO
import com.devshish.internship.domain.util.Constants.REQUEST_SEARCH
import com.devshish.internship.domain.util.Constants.REQUEST_SEARCH_HEADER
import com.devshish.internship.domain.util.Constants.REQUEST_SEARCH_QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ISearchApiRepository {

    @GET(REQUEST_SEARCH)
    suspend fun searchSongs(
        @Header(REQUEST_SEARCH_HEADER) token: String,
        @Query(REQUEST_SEARCH_QUERY) query: String
    ): Response<SongDTO>
}
