package com.devshish.internship.data.repository

import androidx.core.net.toUri
import com.devshish.internship.BuildConfig
import com.devshish.internship.data.api.GeniusSearchApi
import com.devshish.internship.domain.model.SearchSong
import com.devshish.internship.domain.repository.ISearchSongsRepository

class SearchAPIRepository(
    private val api: GeniusSearchApi
) : ISearchSongsRepository {

    override suspend fun searchSongs(query: String): List<SearchSong> {
        return api.searchSongs(
            query = query
        ).response.hits.map {
            SearchSong(
                title = it.result.title,
                artist = it.result.primaryArtist.name,
                imageUri = it.result.headerImageUrl.toUri(),
                lyricsUri = BuildConfig.GENIUS_MAIN_URL + it.result.path,
                lyrics = null
            )
        }
    }
}
