package com.example.fleekpeek.Data.local.repository

import com.example.fleekpeek.Data.local.doa.FavoriteDao
import com.example.fleekpeek.Data.local.mapper.toFavoriteEntity
import com.example.fleekpeek.Data.local.mapper.toTMDBItem
import com.example.fleekpeek.domain.repository.FavoriteRepository
import com.example.fleekpeek.remote.model.TMDBItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<TMDBItem>> =
        dao.getFavorites().map { list -> list.map { it.toTMDBItem() } }

    override suspend fun addFavorite(item: TMDBItem) {
        dao.insertFavorite(item.toFavoriteEntity())
    }

    override suspend fun removeFavorite(item: TMDBItem) {
        dao.deleteFavoriteById(item.id ?: 0)
    }

    override fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)
    override suspend fun clearFavorites() {
        dao.clearFavorites()
    }
}