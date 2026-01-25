package com.example.fleekpeek.domain.use_cases.responseUsecases

import com.example.fleekpeek.Data.local.remote.MovieDetails
import com.example.fleekpeek.Data.local.remote.TvDetails
import com.example.fleekpeek.Data.local.remote.repository.TmdbRepository
import javax.inject.Inject

class DetailsMovieUseCase @Inject  constructor(private var repo: TmdbRepository){
    suspend operator fun invoke(id: Int): MovieDetails = repo.getMoviesDetails(id)
}

class DetailsShowUseCase @Inject  constructor(private var repo: TmdbRepository){
    suspend operator fun invoke(id: Int): TvDetails = repo.getTvDetails(id)

}