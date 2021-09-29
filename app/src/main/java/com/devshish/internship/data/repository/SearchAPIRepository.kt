package com.devshish.internship.data.repository

import androidx.core.net.toUri
import com.devshish.internship.data.api.GeniusSearchApi
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ISearchSongsRepository
import com.devshish.internship.domain.util.Constants.TOKEN

class SearchAPIRepository(
    private val api: GeniusSearchApi
) : ISearchSongsRepository {

    override suspend fun searchSongs(query: String): List<SearchSong> {
        return api.searchSongs(
            // TODO move to Interceptor
            token = TOKEN,
            query = query
        ).response.hits.map {
            SearchSong(
                title = it.result.title,
                artist = it.result.primaryArtist.name,
                imageUri = it.result.headerImageUrl.toUri()
            )
        }
    }
}
