package com.calyrsoft.ucbp1.features.movie.domain.usecase

import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class ToggleLikeUseCase(
    private val repo: IMoviesRepository
) {
    suspend operator fun invoke(movieId: Long): Result<Unit> =
        repo.toggleLike(movieId)
}
