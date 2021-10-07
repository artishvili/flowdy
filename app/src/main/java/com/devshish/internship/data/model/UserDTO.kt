package com.devshish.internship.data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val response: UserResponseDTO
)

data class UserResponseDTO(
    val user: UserInfoDTO
)

data class DomDTO(
    val tag: String
)

data class AboutMeDTO(
    val dom: DomDTO
)

data class UserInfoDTO(
    @SerializedName("about_me")
    val aboutMe: AboutMeDTO,
    @SerializedName("header_image_url")
    val headerImageUrl: String,
    @SerializedName("photo_url")
    val photoUrl: String,
    val name: String
)
