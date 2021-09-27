package com.devshish.internship.domain.model.searchDTO

import com.google.gson.annotations.SerializedName

data class ResultDTO(
    @SerializedName("header_image_url")
    val headerImageUrl: String,
    @SerializedName("primary_artist")
    val primaryArtist: PrimaryArtistDTO,
    val title: String
)
