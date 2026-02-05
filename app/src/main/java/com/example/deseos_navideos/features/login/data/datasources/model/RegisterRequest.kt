package com.example.deseos_navideos.features.login.data.datasources.model

data class RegisterReq(
    val username: String,
    val age: Int,
    val country: String,
    val password: String
)