package com.example.fleekpeek.Data.local.mapper

import com.example.fleekpeek.Data.local.entity.FavoriteEntity
import com.example.fleekpeek.presentations.ui.details.UiMedia
import com.example.fleekpeek.remote.model.TMDBItem


fun FavoriteEntity.toTMDBItem(): TMDBItem =
    TMDBItem(
        id = id,
        title = title,
        poster_path = posterPath,
        backdrop_path = backdropPath,
        overview = overview,
        release_date = date,
        vote_average = rating,
        media_type = mediaType
    )

fun TMDBItem.toFavoriteEntity(): FavoriteEntity =
    FavoriteEntity(
        id = id ?: 0,
        title = title ?: "",
        posterPath = poster_path,
        backdropPath = backdrop_path,
        overview = overview,
        date = release_date,
        rating = vote_average,
        mediaType = media_type ?: "movie"
    )


fun UiMedia.toTMDBItem(): TMDBItem {
    return TMDBItem(
        id = id,
        title = title,
        poster_path = posterPath,
        backdrop_path = backdropPath,
        overview = overview,
        release_date = date,
        vote_average = rating,
        media_type = "movie"
    )
}