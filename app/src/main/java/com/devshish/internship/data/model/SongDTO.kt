package com.devshish.internship.data.model

import com.google.gson.annotations.SerializedName

data class SongDTO(
    val response: ResponseDTO
)

data class ResponseDTO(
    val hits: List<SearchSong>
)

data class SearchSong(
    val result: ResultDTO
)

data class PrimaryArtistDTO(
    val name: String
)

data class ResultDTO(
    @SerializedName("header_image_url")
    val headerImageUrl: String,
    @SerializedName("primary_artist")
    val primaryArtist: PrimaryArtistDTO,
    val title: String,
    val path: String
)
