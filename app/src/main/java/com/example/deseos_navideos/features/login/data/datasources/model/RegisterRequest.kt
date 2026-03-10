package com.example.deseos_navideos.features.login.data.datasources.model

import com.google.gson.annotations.SerializedName

data class RegisterReq(
    val username: String,
    val age: Int,
    val country: String,
    val password: String,
    val role: String,
    @SerializedName("family_code")
    val familyCode: String? = null
)