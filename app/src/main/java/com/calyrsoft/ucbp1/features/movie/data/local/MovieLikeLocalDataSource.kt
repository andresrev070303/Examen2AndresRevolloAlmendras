package com.calyrsoft.ucbp1.features.movie.data.local

import com.calyrsoft.ucbp1.features.movie.data.local.dao.IMovieLikeDao
import com.calyrsoft.ucbp1.features.movie.data.local.entity.MovieLikeEntity

class MovieLikeLocalDataSource(
    private val dao: IMovieLikeDao
) {
    suspend fun likedIds(): List<Long> = dao.likedIds()
    suspend fun setLiked(movieId: Long, liked: Boolean) =
        dao.upsert(MovieLikeEntity(movieId = movieId, liked = liked))
}
