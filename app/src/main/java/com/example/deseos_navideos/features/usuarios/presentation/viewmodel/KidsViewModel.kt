package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKidsUseCase
import com.example.deseos_navideos.features.usuarios.presentation.screens.KidsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KidsViewModel @Inject constructor(
    private val getKidsUseCase: GetKidsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(KidsUiState())
    val uiState: StateFlow<KidsUiState> = _uiState

    init {
        loadKids()
    }

    fun loadKids() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val family_code = UserSession.getFamilyCode()
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if(family_code == null || user_id == null || role == null) return@launch

            val result = getKidsUseCase(family_code, user_id, role)

            result.fold(
                onSuccess = { dashboard ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            kids = dashboard.kids,
                            familyCode = dashboard.familyCode,
                            wishesByKid = dashboard.kids.associate { kid -> kid.id to kid.wishes }
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
}
