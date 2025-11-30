package com.example.fleekpeek.Di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.fleekpeek.Data.local.UserManagerImpl
import com.example.fleekpeek.Data.local.db.FavoriteDatabase
import com.example.fleekpeek.Data.local.doa.FavoriteDao
import com.example.fleekpeek.Data.local.repository.FavoriteRepositoryImpl
import com.example.fleekpeek.Utils.Common
import com.example.fleekpeek.domain.manager.LocalManager
import com.example.fleekpeek.domain.repository.FavoriteRepository
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.AppEntryUseCase
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.ReadEntry
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.SaveEntry
import com.example.fleekpeek.domain.use_cases.favorites_useCases.AddFavoriteUseCase
import com.example.fleekpeek.domain.use_cases.favorites_useCases.FavUseCases
import com.example.fleekpeek.domain.use_cases.favorites_useCases.GetFavoritesUseCase
import com.example.fleekpeek.domain.use_cases.favorites_useCases.IsFavoriteUseCase
import com.example.fleekpeek.domain.use_cases.favorites_useCases.RemoveFavoriteUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.DetailsMovieUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.DetailsShowUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetAnimeTvShowsUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetBollywoodActionMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetComfortTvShowsUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetCrimeMysteryMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetDarkThrillerTvUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetHiddenGemsMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetKDramaTvShowsUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetPopularMovies
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetPopularTv
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetSciFiMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetTrending
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetUpcomingMovies
import com.example.fleekpeek.domain.use_cases.responseUsecases.SearchScreenUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.TMDBUseCase
import com.example.fleekpeek.remote.TmdbApis
import com.example.fleekpeek.remote.repository.TmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalManager = UserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalManager) = AppEntryUseCase(readEntry = ReadEntry(localUserManager),
        saveEntry = SaveEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): TmdbApis{
        return Retrofit.Builder()
            .baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(TmdbApis::class.java)
    }


    @Provides
    @Singleton
    fun getTMDBRepository(): TmdbRepository = com.example.fleekpeek.domain.repository.TmdbRepository(provideNewsApi())


    @Provides
    @Singleton
    fun getTmdbUseCases(repo: TmdbRepository, api: TmdbApis) : TMDBUseCase {
        return TMDBUseCase(
            getTrending = GetTrending(repo),
            getPopularMovies = GetPopularMovies(repo),
            getPopularTv = GetPopularTv(repo),
            getUpcomingMovies = GetUpcomingMovies(repo),
            getMoviesDetails = DetailsMovieUseCase(repo),
            getTvDetails = DetailsShowUseCase(repo),
            getSearchScreenUseCase = SearchScreenUseCase(repo),
            getSciFiMovies = GetSciFiMoviesUseCase(repo),
            getBollywoodActionMovies = GetBollywoodActionMoviesUseCase(repo),
            getCrimeMysteryMovies = GetCrimeMysteryMoviesUseCase(repo),
            getHiddenGemsMovies = GetHiddenGemsMoviesUseCase(repo),
            getDarkThrillerTv = GetDarkThrillerTvUseCase(repo),
            getAnimeTvShows = GetAnimeTvShowsUseCase(repo),
            getComfortTvShows = GetComfortTvShowsUseCase(repo),
            getKDramaTvShows = GetKDramaTvShowsUseCase(repo)
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): FavoriteDatabase {
        return Room.databaseBuilder(app, FavoriteDatabase::class.java, "fleekpeek_db").build()
    }

    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        dao: FavoriteDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideFavoriteUseCases(
        repo: FavoriteRepository
    ): FavUseCases {
        return FavUseCases(
            addFavorite = AddFavoriteUseCase(repo),
            removeFavorite = RemoveFavoriteUseCase(repo),
            getFavorites = GetFavoritesUseCase(repo),
            isFavorite = IsFavoriteUseCase(repo)
        )
    }

    @Provides

    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }




}