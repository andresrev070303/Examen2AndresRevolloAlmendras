package com.calyrsoft.ucbp1.features.post.domain.repository

import com.calyrsoft.ucbp1.features.post.domain.model.CommentModel
import com.calyrsoft.ucbp1.features.post.domain.model.PostModel

interface IPostsRepository {
    suspend fun getAllPosts(): Result<List<PostModel>>
    suspend fun getCommentsByPostId(postId: Int): Result<List<CommentModel>>
}