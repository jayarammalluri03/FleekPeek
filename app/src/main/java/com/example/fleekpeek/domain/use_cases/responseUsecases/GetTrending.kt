package com.example.fleekpeek.domain.use_cases.responseUsecases



import androidx.paging.PagingData
import com.example.fleekpeek.Data.local.remote.model.TMDBItem
import com.example.fleekpeek.Data.local.remote.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrending @Inject constructor(private val repo: TmdbRepository) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> = repo.getTrending(page)
}


class GetPopularMovies @Inject constructor(private val repo: TmdbRepository) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> = repo.getPopularMovies(page)
}


class GetPopularTv @Inject constructor(private val repo: TmdbRepository) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> = repo.getPopularTv(page)
}


class GetUpcomingMovies @Inject constructor(private val repo: TmdbRepository) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem>
    {
        return repo.getUpcomingMovies(page)
    }
}

class SearchScreenUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(query: String): Flow<PagingData<TMDBItem>> {
        return repo.getSearchDetails(query)
    }
}

class GetSciFiMoviesUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getSciFiMovies(page)
    }
}

class GetBollywoodActionMoviesUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getBollywoodActionMovies(page)
    }
}

class GetCrimeMysteryMoviesUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getCrimeMysteryMovies(page)
    }
}

class GetHiddenGemsMoviesUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getHiddenGemsMovies(page)
    }
}

class GetDarkThrillerTvUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getDarkThrillerTv(page)
    }
}

class GetAnimeTvShowsUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getAnimeTvShows(page)
    }
}

class GetComfortTvShowsUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getComfortTvShows(page)
    }
}

class GetKDramaTvShowsUseCase @Inject constructor(
    private val repo: TmdbRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TMDBItem> {
        return repo.getKDramaTvShows(page)
    }
}



