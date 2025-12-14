package com.example.fleekpeek.remote.repository

import androidx.paging.PagingData
import com.example.fleekpeek.remote.MovieDetails
import com.example.fleekpeek.remote.model.TMDBItem
import com.example.fleekpeek.remote.TvDetails
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {
    suspend fun getTrending(page: Int = 1): List<TMDBItem>
    suspend fun getPopularMovies(page: Int = 1): List<TMDBItem>
    suspend fun getPopularTv(page: Int = 1): List<TMDBItem>
    suspend fun getUpcomingMovies(page: Int = 1): List<TMDBItem>
    suspend fun getMoviesDetails(id: Int?): MovieDetails
    suspend fun getTvDetails(id: Int?): TvDetails
    suspend fun getSearchDetails(query: String?): Flow<PagingData<TMDBItem>>
    suspend fun getSciFiMovies(page: Int = 1): List<TMDBItem>
    suspend fun getBollywoodActionMovies(page: Int = 1): List<TMDBItem>
    suspend fun getCrimeMysteryMovies(page: Int = 1): List<TMDBItem>
    suspend fun getHiddenGemsMovies(page: Int = 1): List<TMDBItem>
    suspend fun getDarkThrillerTv(page: Int = 1): List<TMDBItem>
    suspend fun getAnimeTvShows(page: Int = 1): List<TMDBItem>
    suspend fun getComfortTvShows(page: Int = 1): List<TMDBItem>
    suspend fun getKDramaTvShows(page: Int = 1): List<TMDBItem>

}

