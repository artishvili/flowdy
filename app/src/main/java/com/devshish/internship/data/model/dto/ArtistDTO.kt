package com.devshish.internship.data.model.dto

data class ArtistListDTO(
    val artists: ArtistDTO
)

data class ArtistDTO(
    val artist: List<ArtistInfoDTO>
)

data class ArtistInfoDTO(
    val listeners: String,
    val name: String,
    val playcount: String
)
