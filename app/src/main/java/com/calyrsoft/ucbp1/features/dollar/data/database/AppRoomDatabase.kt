package com.calyrsoft.ucbp1.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.movie.data.local.dao.IMovieLikeDao
import com.calyrsoft.ucbp1.features.movie.data.local.entity.MovieLikeEntity

@Database(
    entities = [DollarEntity::class, MovieLikeEntity::class],
    version = 4   // <--- SUBE LA VERSION
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun dollarDao(): IDollarDao
    abstract fun movieLikeDao(): IMovieLikeDao   // <--- NUEVO

    companion object {
        @Volatile private var Instance: AppRoomDatabase? = null
        fun getDatabase(context: Context): AppRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, "dollar_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
