package com.example.fleekpeek.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.WorkManager
import com.example.fleekpeek.Data.local.remote.MovieDetails
import com.example.fleekpeek.Data.local.remote.model.TMDBItem
import com.example.fleekpeek.Data.local.remote.TmdbApis
import com.example.fleekpeek.Data.local.remote.TmdbPagingSource
import com.example.fleekpeek.Data.local.remote.TvDetails
import com.example.fleekpeek.Data.local.remote.repository.TmdbRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TmdbRepository @Inject constructor(
    private val api: TmdbApis,
    private val workManager: WorkManager): TmdbRepository {
    override suspend fun getTrending(page: Int): List<TMDBItem> {
        val resp = api.getTrending(page= page)
        return resp.results
    }



    override suspend fun getPopularMovies(page: Int): List<TMDBItem> {
        val resp = api.getPopularMovies(page=  page)
        return resp.results
    }

    override suspend fun getPopularTv(page: Int): List<TMDBItem> {
        val resp = api.getPopularTv(page= page)
        return resp.results
    }

    override suspend fun getUpcomingMovies(page: Int): List<TMDBItem> {
        val resp = api.getUpcomingMovies(page= page)
        return resp.results
    }

    override suspend fun getMoviesDetails(id: Int?): MovieDetails {
        return api.getMovieDetails(
            movieId = id ?: 0
        )
    }

    override suspend fun getTvDetails(id: Int?): TvDetails {
        return api.getTvDetails(
            tvId = id ?: 0
        )
    }

    override suspend fun getSearchDetails(query: String?): Flow<PagingData<TMDBItem>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TmdbPagingSource (api,query ?: "")
            }
        )
        return pager.flow
    }

    override suspend fun getSciFiMovies(page: Int): List<TMDBItem> {
            val result= api.getSciFiMovies(
                page = page
            )
        return result.results

    }

    override suspend fun getBollywoodActionMovies(page: Int): List<TMDBItem> {
        val result= api.getBollywoodActionMovies(
            page = page
        )
        return result.results
    }

    override suspend fun getCrimeMysteryMovies(page: Int): List<TMDBItem> {
        val result= api.getCrimeMysteryMovies(
            page = page
        )
        return result.results
    }

    override suspend fun getHiddenGemsMovies(page: Int): List<TMDBItem> {
        val result= api.getSciFiMovies(
            page = page
        )
        return result.results
    }

    override suspend fun getDarkThrillerTv(page: Int): List<TMDBItem> {
        val result= api.getDarkThrillerTv(
            page = page
        )
        return result.results
    }

    override suspend fun getAnimeTvShows(page: Int): List<TMDBItem> {
        val result= api.getAnimeTvShows(
            page = page
        )
        return result.results
    }

    override suspend fun getComfortTvShows(page: Int): List<TMDBItem> {
        val result= api.getComfortTvShows(
            page = page
        )
        return result.results
    }

    override suspend fun getKDramaTvShows(page: Int): List<TMDBItem> {
        val result= api.getKDramaTvShows(
            page = page
        )
        return result.results
    }



}