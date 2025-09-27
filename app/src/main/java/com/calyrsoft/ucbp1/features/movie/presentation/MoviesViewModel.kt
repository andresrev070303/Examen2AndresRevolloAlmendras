package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.usecase.GetPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usecase.ToggleLikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase
) : ViewModel() {

    sealed class UiState {
        data object Init : UiState()
        data object Loading : UiState()
        data class Success(val data: List<MovieModel>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Init)
    val state: StateFlow<UiState> = _state.asStateFlow()

    private var lastPage: Int = 1

    fun load(page: Int = 1) {
        lastPage = page
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            val result = getPopularMovies(page)
            result.fold(
                onSuccess = { list ->
                    // 🔽 ordenar: liked primero
                    val ordered = list.sortedByDescending { it.isLiked }
                    _state.value = UiState.Success(ordered)
                },
                onFailure = {
                    _state.value = UiState.Error(it.message ?: "Error desconocido")
                }
            )
        }
    }

    fun toggleLike(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            // Actualiza en base local (Room)
            toggleLikeUseCase(movieId)

            // 🔽 actualización optimista del estado + reordenar
            val current = (_state.value as? UiState.Success)?.data
            if (current != null) {
                val updated = current.map { m ->
                    if (m.id == movieId) m.copy(isLiked = !m.isLiked) else m
                }.sortedByDescending { it.isLiked }

                _state.value = UiState.Success(updated)
            } else {
                // Si no hay lista en memoria (Init/Error), recargar y ordenar
                val result = getPopularMovies(lastPage)
                result.fold(
                    onSuccess = { list ->
                        val ordered = list.sortedByDescending { it.isLiked }
                        _state.value = UiState.Success(ordered)
                    },
                    onFailure = {
                        _state.value = UiState.Error(it.message ?: "Error desconocido")
                    }
                )
            }
        }
    }
}
