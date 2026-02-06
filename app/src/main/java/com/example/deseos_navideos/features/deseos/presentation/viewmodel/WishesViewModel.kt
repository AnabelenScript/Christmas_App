package com.example.deseos_navideos.features.deseos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWish_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWish_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishes_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishes_UseCase
import com.example.deseos_navideos.features.deseos.presentation.screens.WishesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishesViewModel(
    private val createwishUsecase: CreateWish_UseCase,
    private val getWishes: GetWishes_UseCase,
    private val updateWIsh: UpdateWishes_UseCase,
    private val deleteWish: DeleteWish_UseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(WishesUiState())
    val uiState = _uiState.asStateFlow()

    var newWish: String = ""

    init {
        loadWishes()
    }

    val kid_id = 2
    val user_id = 1
    val role = "santa"

    fun loadWishes(){
        _uiState.update { it.copy(isLoading = true) }
        val selected_id = if (role == "santa") kid_id else user_id
        viewModelScope.launch {
            val r = getWishes(selected_id, role)
            _uiState.update { currentState ->
                r.fold(
                    onSuccess = {list ->
                        currentState.copy(isLoading = false, wishes = list)
                    },
                    onFailure = {error ->
                        currentState.copy(isLoading = false, errorMessage = error.message)
                    }
                )
            }
        }
    }

    fun addWish(){
        if (role == "santa") return
        if (newWish == "") return
        viewModelScope.launch {
            createwishUsecase(newWish, user_id, role)
            loadWishes()
        }
    }

    fun editWish(thing: String, id: Int){
        if (role == "santa") return
        if (thing == "") return
        viewModelScope.launch {
            updateWIsh(id, thing, user_id, role)
            loadWishes()
        }
    }

    fun removeWish(id: Int){
        if (role == "santa") return
        viewModelScope.launch {
            deleteWish(id, user_id, role)
            loadWishes()
        }
    }
}