package com.example.fleekpeek.domain.use_cases.favorites_useCases

data class FavUseCases(
    val addFavorite: AddFavoriteUseCase,
    val removeFavorite: RemoveFavoriteUseCase,
    val getFavorites: GetFavoritesUseCase,
    val isFavorite: IsFavoriteUseCase,
    val clearFavorites: ClearFavorites
)