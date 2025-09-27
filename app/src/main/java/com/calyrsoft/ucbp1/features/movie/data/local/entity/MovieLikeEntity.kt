package com.calyrsoft.ucbp1.features.movie.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_likes")
data class MovieLikeEntity(
    @PrimaryKey @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "liked") val liked: Boolean = true,
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)
