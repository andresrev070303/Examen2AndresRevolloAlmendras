package com.calyrsoft.ucbp1.features.post.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.post.domain.model.CommentModel
import com.calyrsoft.ucbp1.features.post.domain.model.PostModel
import com.calyrsoft.ucbp1.features.post.domain.usecase.GetAllPostsUseCase
import com.calyrsoft.ucbp1.features.post.domain.usecase.GetCommentsByPostIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getAllPosts: GetAllPostsUseCase,
    private val getCommentsByPostId: GetCommentsByPostIdUseCase
) : ViewModel() {

    sealed class UiState {
        object Init : UiState()
        object Loading : UiState()
        data class Success(val posts: List<PostModel>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Init)
    val state: StateFlow<UiState> = _state.asStateFlow()

    // Estado para comentarios por post
    private val _comments = MutableStateFlow<Map<Int, List<CommentModel>>>(emptyMap())
    val comments: StateFlow<Map<Int, List<CommentModel>>> = _comments.asStateFlow()

    // Estado para saber qué posts están cargando comentarios
    private val _loadingComments = MutableStateFlow<Set<Int>>(emptySet())
    val loadingComments: StateFlow<Set<Int>> = _loadingComments.asStateFlow()

    fun loadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            val result = getAllPosts()
            result.fold(
                onSuccess = { posts ->
                    _state.value = UiState.Success(posts)
                },
                onFailure = { exception ->
                    _state.value = UiState.Error(exception.message ?: "Error desconocido")
                }
            )
        }
    }

    fun loadComments(postId: Int) {
        if (_comments.value.containsKey(postId)) return

        viewModelScope.launch(Dispatchers.IO) {
            _loadingComments.value = _loadingComments.value + postId

            val result = getCommentsByPostId(postId)
            result.fold(
                onSuccess = { comments ->
                    _comments.value = _comments.value + (postId to comments)
                },
                onFailure = {
                    _comments.value = _comments.value + (postId to emptyList())
                }
            )

            _loadingComments.value = _loadingComments.value - postId
        }
    }
}