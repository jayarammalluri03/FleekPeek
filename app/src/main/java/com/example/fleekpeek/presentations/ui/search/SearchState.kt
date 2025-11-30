package com.example.fleekpeek.presentations.ui.search

data class SearchState (val searchQuery: String= "", val error: Error?= null, val isLoading: Boolean= false)