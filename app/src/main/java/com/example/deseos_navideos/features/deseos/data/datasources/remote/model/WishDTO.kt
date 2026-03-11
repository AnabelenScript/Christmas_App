package com.example.deseos_navideos.features.deseos.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class WishesResponseDto(
    val wishes: List<WishDto>
)

data class WishDto(
    val id: Int,

    @SerializedName("object")
    val thing: String,

    @SerializedName("id_user")
    val idUser: Int,

    val username: String,

    val state: String,

    @SerializedName("photo_url")
    val photoUrl: String?
)

data class CreateWishDto(
    val thing: String
)

data class UpdateWishDto(
    val thing: String
)

data class UpdateWishStateDto(
    val state: String
)