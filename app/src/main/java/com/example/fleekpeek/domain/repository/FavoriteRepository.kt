package com.example.fleekpeek.domain.repository

import com.example.fleekpeek.remote.model.TMDBItem
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<TMDBItem>>
    suspend fun addFavorite(item: TMDBItem)
    suspend fun removeFavorite(item: TMDBItem)
    fun isFavorite(id: Int): Flow<Boolean>
    suspend fun clearFavorites()
}
