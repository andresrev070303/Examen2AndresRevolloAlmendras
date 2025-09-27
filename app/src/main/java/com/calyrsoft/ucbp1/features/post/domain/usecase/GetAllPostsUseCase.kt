package com.calyrsoft.ucbp1.features.post.domain.usecase

import com.calyrsoft.ucbp1.features.post.domain.model.PostModel
import com.calyrsoft.ucbp1.features.post.domain.repository.IPostsRepository

class GetAllPostsUseCase(
    private val repository: IPostsRepository
) {
    suspend operator fun invoke(): Result<List<PostModel>> {
        return repository.getAllPosts()
    }
}
