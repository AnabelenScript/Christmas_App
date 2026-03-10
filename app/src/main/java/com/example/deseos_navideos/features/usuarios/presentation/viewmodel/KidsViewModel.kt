package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KidsViewModel(
    private val getKidsUseCase: GetKids_UseCase,
    private val getWishesUseCase: GetWishesUseCase,
    private val dataStorage: DataStorage
) : ViewModel() {
    private val _kids = MutableStateFlow<List<Users>>(emptyList())
    val kids: StateFlow<List<Users>> = _kids
    private val _wishesByKid = MutableStateFlow<Map<Int, List<Wish>>>(emptyMap())
    val wishesByKid: StateFlow<Map<Int, List<Wish>>> = _wishesByKid

    private fun getUserId(): Int {
        val loginRes = dataStorage.getLoginResponse()
        return loginRes?.user?.id ?: 0
    }

    private fun getRole(): String {
        val loginRes = dataStorage.getLoginResponse()
        return loginRes?.user?.role ?: "guest"
    }

    private fun getFamilyCode(): String {
        val loginRes = dataStorage.getLoginResponse()
        return loginRes?.user?.familyCode ?: ""
    }

    init {
        loadKids()
    }

    fun loadKids() {
        val role = getRole()
        val userId = getUserId()
        val familyCode = getFamilyCode()

        if (role != "parent") return

        viewModelScope.launch {
            val result = getKidsUseCase(familyCode, userId, role)
            result.onSuccess { kidsList ->
                _kids.value = kidsList
                loadWishesForKids(kidsList, role)
            }
            result.onFailure {
                _kids.value = emptyList()
                _wishesByKid.value = emptyMap()
            }
        }
    }

    private fun loadWishesForKids(kidsList: List<Users>, role: String) {
        viewModelScope.launch {
            val wishesMap = mutableMapOf<Int, List<Wish>>()
            kidsList.forEach { kid ->
                // For a parent viewing a kid's wishes, we might need the family code or the kid's ID
                // The API GET /wishes/:code takes a code. If it's for a kid, it might be their family code.
                // Assuming for now it needs a string identifier.
                val result = getWishesUseCase(kid.id.toString(), kid.id, role)
                result.onSuccess { wishes ->
                    wishesMap[kid.id] = wishes
                }
                result.onFailure {
                    wishesMap[kid.id] = emptyList()
                }
            }
            _wishesByKid.value = wishesMap
        }
    }
}
