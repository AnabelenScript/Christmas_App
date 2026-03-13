package com.example.deseos_navideos.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kids")
data class KidEntity(

    @PrimaryKey
    val id: Int,

    val username: String,

    val audioUrl: String?,

    val familyCode: String
)