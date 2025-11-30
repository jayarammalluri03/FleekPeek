package com.example.fleekpeek.presentations.ui.tvmovie

import com.example.fleekpeek.presentations.ui.details.DetailsEvents

sealed class TVMovieEvent {

    data class loadData(val isMovie: Boolean): TVMovieEvent()

}