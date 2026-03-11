package com.example.deseos_navideos.features.deseos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishUseCase
import com.example.deseos_navideos.features.deseos.presentation.screens.WishesUiState
import com.example.deseos_navideos.features.login.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishesViewModel @Inject constructor(
    private val createWishUseCase: CreateWishUseCase,
    private val getWishesUseCase: GetWishesUseCase,
    private val updateWishUseCase: UpdateWishUseCase,
    private val deleteWishUseCase: DeleteWishUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadWishes()
    }

    fun onNewWishChange(value: String) {
        _uiState.update { it.copy(newWish = value) }
    }

    fun loadWishes() {

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            val family_code = UserSession.getFamilyCode()
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if(family_code == null || user_id == null || role == null) return@launch

            val result = getWishesUseCase(family_code, user_id, role)

            result.fold(
                onSuccess = { list ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wishes = list
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    fun addWish() {

        val wish = _uiState.value.newWish

        if (wish.isBlank()) return

        viewModelScope.launch {

            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if(user_id == null || role == null) return@launch

            val result = createWishUseCase(wish, user_id, role)

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(newWish = "") }
                    loadWishes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(errorMessage = error.message) }
                }
            )
        }
    }

    fun editWish(id: Int, description: String) {

        if (description.isBlank()) return

        viewModelScope.launch {

            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if(user_id == null || role == null) return@launch

            val result = updateWishUseCase(id, description, user_id, role)

            result.fold(
                onSuccess = {
                    loadWishes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(errorMessage = error.message) }
                }
            )
        }
    }

    fun removeWish(id: Int) {

        viewModelScope.launch {

            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if(user_id == null || role == null) return@launch

            val result = deleteWishUseCase(id, user_id, role)

            result.fold(
                onSuccess = {
                    _uiState.update { state ->
                        state.copy(
                            wishes = state.wishes.filterNot { it.id == id }
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            )
        }
    }
}