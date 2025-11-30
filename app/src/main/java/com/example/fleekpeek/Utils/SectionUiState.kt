package com.example.fleekpeek.Utils

import com.example.fleekpeek.remote.model.TMDBItem

data class SectionUiState(
    val items: List<TMDBItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)