package com.example.fleekpeek.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fleekpeek.domain.repository.TmdbRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RandomSeriesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val tmdbRepository: TmdbRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val seriesList = tmdbRepository.getPopularSeries(page = 1)

            if (seriesList.isEmpty()) return Result.retry()

            val randomSeries = seriesList.random()

            val title = randomSeries.name ?: randomSeries.original_name ?: "Random Series"
            val overview = randomSeries.overview

            RandomSeriesNotifier.showSeriesNotification(
                context = applicationContext,
                title = title,
                overview = overview
            )

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
