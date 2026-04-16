package com.example.deseos_navideos.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishes")
data class WishEntity(

    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,

    val id: Int, // Remote ID (0 if pending)

    val wish: String,

    val idUser: Int,

    val username: String?,

    val state: String,

    val photoUrl: String?,

    val syncState: String, // PENDING, SYNCED

    val localFilePath: String? = null,

    val taskType: String = "WISH", // WISH, PHOTO, AUDIO

    val role: String? = null // Role of the user who created the task
)