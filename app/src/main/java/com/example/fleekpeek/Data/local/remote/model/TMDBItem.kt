package com.example.fleekpeek.Data.local.remote.model

data class TMDBItem(
    val id: Int? = null,
    val media_type: String? = null,
    val title: String? = null,
    val name: String? = null,
    val original_title: String? = null,
    val original_name: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val profile_path: String? = null,
    val release_date: String? = null,
    val first_air_date: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    val popularity: Double? = null,
    val genre_ids: List<Int>? = null,
    val adult: Boolean? = null,
    val known_for: List<TMDBItem>? = null
) {

    fun displayTitle(): String =
        title ?: name ?: original_title ?: original_name ?: "Unknown"

    fun displayPoster(): String? =
        poster_path ?: profile_path

    fun displayDate(): String? =
        release_date ?: first_air_date
}
