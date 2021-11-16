package com.devshish.internship.data.model.dto

data class ArtistListDTO(
    val artists: ArtistDTO
)

data class ArtistDTO(
    val artist: List<ArtistInfoDTO>
)

data class ArtistInfoDTO(
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)
