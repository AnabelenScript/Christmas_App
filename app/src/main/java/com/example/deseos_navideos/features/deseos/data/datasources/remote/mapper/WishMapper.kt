package com.example.deseos_navideos.features.deseos.data.datasources.remote.mapper

import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishDTO
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

fun WishDTO.toDomain(): Wish {
    return Wish (
        id = this.id,
        wish = this.thing,
        id_user = this.id_user,
        is_editing = false
    )
}