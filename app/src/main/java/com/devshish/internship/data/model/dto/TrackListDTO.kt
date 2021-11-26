package com.devshish.internship.data.model.dto

import com.google.gson.annotations.SerializedName

data class TrackListDTO(
    val tracks: TrackDTO
)

data class TrackDTO(
    val track: List<TrackInfoDTO>
)

data class TrackInfoDTO(
    val artist: TrackArtistDTO,
    val listeners: String,
    val name: String,
    @SerializedName("playcount") val playCount: String,
    val url: String
)

data class TrackArtistDTO(
    val name: String
)
