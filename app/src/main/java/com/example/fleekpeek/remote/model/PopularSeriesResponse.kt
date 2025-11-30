package com.example.fleekpeek.remote.model

data class PopularSeriesResponse(
    val results: List<SeriesDto>
)

data class SeriesDto(
    val id: Int,
    val name: String?,
    val original_name: String?,
    val overview: String?
)