package com.devshish.internship.domain.repository

import com.devshish.internship.domain.model.SearchSong

interface ISearchSongsRepository {

    suspend fun searchSongs(
        query: String
    ): List<SearchSong>
}
