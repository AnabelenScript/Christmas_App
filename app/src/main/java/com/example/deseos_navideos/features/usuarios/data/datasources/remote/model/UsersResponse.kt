package com.example.deseos_navideos.features.usuarios.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("family_code")
    val familyCode: String,
    val kids: List<KidDashboardDTO>
)

data class KidDashboardDTO(
    val id: Int,
    val username: String,
    @SerializedName("audio_url")
    val audioUrl: String?,
    val wishes: List<WishDashboardDTO>
)

data class WishDashboardDTO(
    val id: Int,
    @SerializedName("object")
    val thing: String,
    val state: String,
    @SerializedName("photo_url")
    val photoUrl: String?
)
