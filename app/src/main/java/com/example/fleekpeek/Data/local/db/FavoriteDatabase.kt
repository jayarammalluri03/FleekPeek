package com.example.fleekpeek.Data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fleekpeek.Data.local.doa.FavoriteDao
import com.example.fleekpeek.Data.local.entity.FavoriteEntity

@Database(
    entities = [
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}