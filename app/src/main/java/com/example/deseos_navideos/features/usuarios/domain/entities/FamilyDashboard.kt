package com.example.deseos_navideos.features.usuarios.domain.entities

data class FamilyDashboard(
    val familyCode: String,
    val kids: List<Kid>
)
