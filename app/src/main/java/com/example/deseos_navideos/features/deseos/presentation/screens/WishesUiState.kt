package com.example.deseos_navideos.features.deseos.presentation.screens

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
data class WishesUiState(
    val isLoading: Boolean = true,
    val wishes: List<Wish> = emptyList(),
    val newWish: String = "",
    val editWish: String = "",
    val errorMessage: String? = null,
    val deletedWishId: Int? = null
)
