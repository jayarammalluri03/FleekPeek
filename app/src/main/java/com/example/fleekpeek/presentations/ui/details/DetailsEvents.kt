package com.example.fleekpeek.presentations.ui.details

sealed class DetailsEvents {

    data class getIdMediaType(val id: Int, val mediaType: String): DetailsEvents()

    data class toggleFavorite(val isFav: Boolean, val data: UiMedia): DetailsEvents()

}