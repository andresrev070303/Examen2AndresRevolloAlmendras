package com.calyrsoft.ucbp1.features.post.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.calyrsoft.ucbp1.features.post.domain.model.CommentModel
import com.calyrsoft.ucbp1.features.post.domain.model.PostModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    vm: PostsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()
    val comments by vm.comments.collectAsState()
    val loadingComments by vm.loadingComments.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadPosts()
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            is PostsViewModel.UiState.Init -> {
                Text(
                    text = "Inicializando...",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is PostsViewModel.UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is PostsViewModel.UiState.Error -> {
                Text(
                    text = "Error: ${s.message}",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
            is PostsViewModel.UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = s.posts) { post ->
                        PostCardWithComments(
                            post = post,
                            comments = comments[post.id] ?: emptyList(),
                            isLoadingComments = loadingComments.contains(post.id),
                            hasComments = comments.containsKey(post.id),
                            onLoadComments = { vm.loadComments(post.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PostCardWithComments(
    post: PostModel,
    comments: List<CommentModel>,
    isLoadingComments: Boolean,
    hasComments: Boolean,
    onLoadComments: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // User ID Badge
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "User ${post.userId}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Title
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Body
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Comments Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!hasComments && !isLoadingComments) {
                            onLoadComments()
                        }
                        isExpanded = !isExpanded
                    }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when {
                        isLoadingComments -> "Cargando comentarios..."
                        hasComments -> "Ver comentarios (${comments.size})"
                        else -> "Cargar comentarios"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                if (isLoadingComments) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Ocultar" else "Mostrar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Comments Section
            AnimatedVisibility(
                visible = isExpanded && hasComments,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    comments.forEach { comment ->
                        CommentItem(
                            comment = comment,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: CommentModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Name and Email
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = comment.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = comment.email,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Comment Body
            Text(
                text = comment.body,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}