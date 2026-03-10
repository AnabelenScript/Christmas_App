package com.example.deseos_navideos.features.deseos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWishUseCase
import com.example.deseos_navideos.features.deseos.presentation.screens.WishesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishesViewModel(
    private val createWishUseCase: CreateWishUseCase,
    private val getWishesUseCase: GetWishesUseCase,
    private val updateWishesUseCase: UpdateWishesUseCase,
    private val deleteWishUseCase: DeleteWishUseCase,
    private val dataStorage: DataStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishesUiState())
    val uiState = _uiState.asStateFlow()

    var newWish: String = ""

    init {
        loadWishes()
    }

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

    fun loadWishes() {
        _uiState.update { it.copy(isLoading = true) }
        val userId = getUserId()
        val role = getRole()
        val familyCode = getFamilyCode()

        viewModelScope.launch {
            val result = getWishesUseCase(familyCode, userId, role)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(isLoading = false, wishes = list)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, errorMessage = error.message)
                    }
                )
            }
        }
    }

    fun addWish() {
        val role = getRole()
        val userId = getUserId()
        if (role == "santa") return
        if (newWish.isBlank()) return

        viewModelScope.launch {
            createWishUseCase(newWish, userId, role)
            loadWishes()
        }
    }

    fun editWish(description: String, id: Int) {
        val role = getRole()
        val userId = getUserId()
        if (role == "santa") return
        if (description.isBlank()) return

        viewModelScope.launch {
            updateWishesUseCase(id, description, userId, role)
            loadWishes()
        }
    }

    fun removeWish(id: Int) {
        val role = getRole()
        val userId = getUserId()
        if (role == "santa") return

        viewModelScope.launch {
            try {
                // Ejecuta el borrado en el backend
                deleteWishUseCase(id, userId, role)

                // Actualiza el estado local para reflejar la eliminación
                _uiState.update { currentState ->
                    currentState.copy(
                        wishes = currentState.wishes.filterNot { it.id == id },
                        deletedWishId = id,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(errorMessage = e.message)
                }
            }
        }
    }
}
