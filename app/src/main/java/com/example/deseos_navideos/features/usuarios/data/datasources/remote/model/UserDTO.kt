package com.example.deseos_navideos.features.usuarios.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class FamilyDashboardDto(
    @SerializedName("family_code")
    val familyCode: String,

    val kids: List<KidDto>
)

data class KidDto(
    val id: Int,
    val username: String,

    @SerializedName("audio_url")
    val audioUrl: String?,

    val wishes: List<KidWishDto>
)

data class KidWishDto(
    val id: Int,

    @SerializedName("object")
    val thing: String,

    val state: String,

    @SerializedName("photo_url")
    val photoUrl: String?
)

data class UpdateUserDto(
    val username: String,
    val age: Int,
    val country: String,
    val password: String
)

data class MessageResponseDto(
    val message: String
)

data class UploadResponseDto(
    val message: String,
    val url: String
)