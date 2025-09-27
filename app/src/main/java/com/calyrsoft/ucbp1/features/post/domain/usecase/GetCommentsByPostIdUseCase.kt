package com.calyrsoft.ucbp1.features.post.domain.usecase

import com.calyrsoft.ucbp1.features.post.domain.model.CommentModel
import com.calyrsoft.ucbp1.features.post.domain.repository.IPostsRepository

class GetCommentsByPostIdUseCase(
    private val repository: IPostsRepository
) {
    suspend operator fun invoke(postId: Int): Result<List<CommentModel>> {
        return repository.getCommentsByPostId(postId)
    }
}