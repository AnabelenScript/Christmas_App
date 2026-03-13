package com.example.deseos_navideos.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey
    val id: Int,

    val username: String,

    val age: Int,

    val country: String?,

    val password: String,

    val familyCode: String?,

    val role: String
)