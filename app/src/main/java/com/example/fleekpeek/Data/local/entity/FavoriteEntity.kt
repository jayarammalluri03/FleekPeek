package com.example.fleekpeek.Data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val mediaType: String,
    val rating: Double?,
    val date: String?
)
