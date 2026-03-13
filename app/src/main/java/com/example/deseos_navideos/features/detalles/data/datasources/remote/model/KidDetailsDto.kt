package com.example.deseos_navideos.features.detalles.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class KidDetailsDto(
    val id: Int,
    val username: String,
    @SerializedName("audio_url")
    val audioUrl: String?,
    val wishes: List<WishDetailsDto>
)

data class WishDetailsDto(
    val id: Int,
    @SerializedName("object")
    val thing: String,
    val state: String,
    @SerializedName("photo_url")
    val photoUrl: String?
)
