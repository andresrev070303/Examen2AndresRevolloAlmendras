package com.calyrsoft.ucbp1.features.post.data.repository

import com.calyrsoft.ucbp1.features.post.data.datasource.PostsRemoteDataSource
import com.calyrsoft.ucbp1.features.post.domain.model.CommentModel
import com.calyrsoft.ucbp1.features.post.domain.model.PostModel
import com.calyrsoft.ucbp1.features.post.domain.repository.IPostsRepository

class PostsRepository(
    private val remote: PostsRemoteDataSource
) : IPostsRepository {

    override suspend fun getAllPosts(): Result<List<PostModel>> {
        val response = remote.getAllPosts()
        return response.fold(
            onSuccess = { dtos ->
                val models = dtos.map { dto ->
                    PostModel(
                        userId = dto.userId,
                        id = dto.id,
                        title = dto.title,
                        body = dto.body
                    )
                }
                Result.success(models)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

    override suspend fun getCommentsByPostId(postId: Int): Result<List<CommentModel>> {
        val response = remote.getCommentsByPostId(postId)
        return response.fold(
            onSuccess = { dtos ->
                val models = dtos.map { dto ->
                    CommentModel(
                        postId = dto.postId,
                        id = dto.id,
                        name = dto.name,
                        email = dto.email,
                        body = dto.body
                    )
                }
                Result.success(models)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}