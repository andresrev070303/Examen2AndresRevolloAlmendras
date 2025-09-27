package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow   // si usas Opción B, bórralo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DollarViewModel(
    private val fetchDollarUseCase: FetchDollarUseCase
) : ViewModel() {

    sealed class DollarUIState {
        data object Loading : DollarUIState()
        data class Success(val data: DollarModel) : DollarUIState()
        data class Error(val message: String) : DollarUIState()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    // Opción A:
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()
    // Opción B (si falla asStateFlow):
    // val uiState: StateFlow<DollarUIState> get() = _uiState

    init { observeDollar() }

    private fun observeDollar() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDollarUseCase()
                .catch { e -> _uiState.value = DollarUIState.Error(e.message ?: "Error desconocido") }
                .collect { model -> _uiState.value = DollarUIState.Success(model) }
        }
    }
}
