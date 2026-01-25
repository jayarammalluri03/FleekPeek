package com.example.fleekpeek.Data.local.remote

import com.example.fleekpeek.Utils.Common
import com.example.fleekpeek.Data.local.remote.model.TMDBResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApis {

    @GET("trending/all/day")
    suspend fun getTrending(
        @Query("api_key") key: String= Common.API_KEY,
        @Query("page") page: Int = 1
    ): TMDBResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String= Common.API_KEY,
        @Query("page") page: Int = 1
    ): TMDBResponse

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") key: String= Common.API_KEY,
        @Query("page") page: Int = 1
    ): TMDBResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") key: String= Common.API_KEY,
        @Query("page") page: Int = 1
    ): TMDBResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String= Common.API_KEY
    ): MovieDetails


    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String= Common.API_KEY
    ): TvDetails

    @GET("search/tv")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false
        ,@Query("api_key") apiKey: String= Common.API_KEY
    ): TMDBResponse

    @GET("discover/movie")
    suspend fun getSciFiMovies(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "878", // Sci-Fi
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    @GET("discover/movie")
    suspend fun getBollywoodActionMovies(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "28",   // Action
        @Query("with_original_language") languageCode: String = "hi",
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    // 3) Crime & Mystery Movies
    @GET("discover/movie")
    suspend fun getCrimeMysteryMovies(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "80,9648", // Crime, Mystery
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    // 4) Hidden Gems (rating > 8, enough votes, low-ish popularity)
    @GET("discover/movie")
    suspend fun getHiddenGemsMovies(
        @Query("page") page: Int = 1,
        @Query("vote_average.gte") minRating: Double = 8.0,
        @Query("vote_count.gte") minVotes: Int = 100,
        @Query("sort_by") sortBy: String = "popularity.asc",
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    @GET("discover/tv")
    suspend fun getDarkThrillerTv(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "9648,53", // Mystery, Thriller
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    // 6) Anime TV Shows
    @GET("discover/tv")
    suspend fun getAnimeTvShows(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "16",   // Animation
        @Query("with_original_language") languageCode: String = "ja",
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    // 7) Comfort Shows (Comedy + Family)
    @GET("discover/tv")
    suspend fun getComfortTvShows(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String = "35,10751", // Comedy, Family
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    // 8) K-Drama Favorites
    @GET("discover/tv")
    suspend fun getKDramaTvShows(
        @Query("page") page: Int = 1,
        @Query("with_original_language") languageCode: String = "ko",
        @Query("api_key") apiKey: String = Common.API_KEY,
        @Query("language") language: String = "en-US"
    ): TMDBResponse



}