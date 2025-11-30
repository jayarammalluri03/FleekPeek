package com.example.fleekpeek.presentations.ui.search

sealed class SearchEvent {
    data class updateSeachQuery(val searchQuery: String): SearchEvent()
    data object searchitems: SearchEvent()
    object ClearSearch : SearchEvent()
}
