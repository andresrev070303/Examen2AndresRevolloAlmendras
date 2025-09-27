package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.datasource.MoviesRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.data.local.MovieLikeLocalDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

private const val TMDB_IMAGE_BASE_W185 = "https://image.tmdb.org/t/p/w185"

class MoviesRepository(
    private val remote: MoviesRemoteDataSource,
    private val likesLocal: MovieLikeLocalDataSource
) : IMoviesRepository {

    override suspend fun getPopularWithLikes(page: Int): Result<List<MovieModel>> = runCatching {
        val response = remote.getPopularMovies(page).getOrThrow()
        val liked = likesLocal.likedIds().toSet()

        response.results.map { dto ->
            MovieModel(
                id = dto.id,
                title = dto.title,
                imageUrl = dto.backdropPath?.let { "$TMDB_IMAGE_BASE_W185$it" },
                isLiked = liked.contains(dto.id)
            )
        }
    }

    override suspend fun toggleLike(movieId: Long): Result<Unit> = runCatching {
        val likedNow = likesLocal.likedIds().contains(movieId)
        likesLocal.setLiked(movieId, !likedNow)
    }
}
