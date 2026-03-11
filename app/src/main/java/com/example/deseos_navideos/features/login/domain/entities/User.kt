package com.example.deseos_navideos.features.login.domain.entities

data class User(
    val id: Int,
    val username: String,
    val age: Int,
    val country: String?,
    val password: String,
    val familyCode: String?,
    val role: String
)