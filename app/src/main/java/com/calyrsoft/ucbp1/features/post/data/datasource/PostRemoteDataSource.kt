package com.calyrsoft.ucbp1.features.post.data.datasource

import com.calyrsoft.ucbp1.features.post.data.api.PostsService
import com.calyrsoft.ucbp1.features.post.data.api.dto.CommentDto
import com.calyrsoft.ucbp1.features.post.data.api.dto.PostDto

class PostsRemoteDataSource(
    private val service: PostsService
) {
    suspend fun getAllPosts(): Result<List<PostDto>> {
        return try {
            val response = service.getPosts()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener los posts"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCommentsByPostId(postId: Int): Result<List<CommentDto>> {
        return try {
            val response = service.getCommentsByPostId(postId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener los comentarios"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}