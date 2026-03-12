package com.example.deseos_navideos.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishes")
data class WishEntity(

    @PrimaryKey
    val id: Int,

    val wish: String,

    val idUser: Int,

    val username: String?,

    val state: String,

    val photoUrl: String?
)