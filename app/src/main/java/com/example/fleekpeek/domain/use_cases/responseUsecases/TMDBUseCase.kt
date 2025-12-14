package com.example.fleekpeek.domain.use_cases.responseUsecases

import com.example.fleekpeek.remote.TvDetails

data class TMDBUseCase(
    val getTrending: GetTrending,
    val getPopularMovies: GetPopularMovies,
    val getPopularTv: GetPopularTv,
    val getUpcomingMovies: GetUpcomingMovies,
    val getMoviesDetails: DetailsMovieUseCase,
    val getTvDetails: DetailsShowUseCase,
    val getSearchScreenUseCase: SearchScreenUseCase,
    val getSciFiMovies: GetSciFiMoviesUseCase,
    val getBollywoodActionMovies: GetBollywoodActionMoviesUseCase,
    val getCrimeMysteryMovies: GetCrimeMysteryMoviesUseCase,
    val getHiddenGemsMovies: GetHiddenGemsMoviesUseCase,
    val getDarkThrillerTv: GetDarkThrillerTvUseCase,
    val getAnimeTvShows: GetAnimeTvShowsUseCase,
    val getComfortTvShows: GetComfortTvShowsUseCase,
    val getKDramaTvShows: GetKDramaTvShowsUseCase,
)