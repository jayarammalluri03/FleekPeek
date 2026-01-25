package com.example.fleekpeek.Data.local.remote.model

data class TMDBResponse(
    val page: Int = 1,
    val results: List<TMDBItem> = emptyList(),
    val total_pages: Int,
    val total_result: Int
)