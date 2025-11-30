package com.example.fleekpeek.domain.use_cases.favorites_useCases

import com.example.fleekpeek.domain.repository.FavoriteRepository
import com.example.fleekpeek.remote.model.TMDBItem
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(item: TMDBItem) = repo.addFavorite(item)
}

class RemoveFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(item: TMDBItem) = repo.removeFavorite(item)
}

class GetFavoritesUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    operator fun invoke() = repo.getFavorites()
}

class IsFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    operator fun invoke(id: Int) = repo.isFavorite(id)
}
