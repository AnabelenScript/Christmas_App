package com.example.deseos_navideos.features.usuarios.data.datasources.local.mapper

import com.example.deseos_navideos.core.database.entities.KidEntity
import com.example.deseos_navideos.core.database.entities.WishEntity
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidWishDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.WishDashboardDTO

fun KidDto.toEntity(familyCode: String): KidEntity {
    return KidEntity(
        id = id,
        username = username,
        audioUrl = audioUrl,
        familyCode = familyCode
    )
}


fun KidWishDto.toEntity(userId: Int): WishEntity {
    return WishEntity(
        id = id,
        wish = thing,
        idUser = userId,
        username = null,
        state = state,
        photoUrl = photoUrl,
        syncState = "SYNCED",
        taskType = "WISH",
        role = "niño"
    )
}