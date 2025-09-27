package com.calyrsoft.ucbp1.features.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.movie.data.local.entity.MovieLikeEntity

@Dao
interface IMovieLikeDao {

    @Query("SELECT movie_id FROM movie_likes WHERE liked = 1")
    suspend fun likedIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(like: MovieLikeEntity)
}
