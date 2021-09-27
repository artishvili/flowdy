package com.devshish.internship.data.repository

import com.devshish.internship.domain.model.searchDTO.SongDTO
import com.devshish.internship.domain.repository.ISearchApiRepository
import com.devshish.internship.domain.util.Constants.TOKEN
import retrofit2.Response

class SearchAPIRepository(
    private val apiRepository: ISearchApiRepository
) {

    /*override suspend fun searchSongs(token: String, query: String): Response<SongDTO> {
//        return Response<SongDTO>(song)
        return searchSongs(token, query)
    }*/

    suspend fun searchSongs(
        query: String
    ): Response<SongDTO> =
        apiRepository.searchSongs(
            token = TOKEN,
            query = query
        )
}
