package com.example.fleekpeek.presentations.ui.details


import CollapsingPosterScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fleekpeek.presentations.viewModels.DetailsUiState
import com.example.fleekpeek.presentations.viewModels.DetailsViewModel
import com.example.fleekpeek.remote.MovieDetails
import com.example.fleekpeek.remote.TvDetails
import kotlinx.coroutines.flow.MutableStateFlow


data class UiMedia(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val date: String?,
    val rating: Double?,
    val tagline: String?,
    val genres: String?,
    val runtime: Int?,
    val status: String?,
    val seasonsCount: Int?,
    val homepage: String?,
    val productionCompanies: String?
)

private fun MovieDetails.toUiMedia(): UiMedia {
    return UiMedia(
        id = id ?: 0,
        title = title ?: "",
        posterPath = poster_path,
        backdropPath = backdrop_path,
        overview = overview,
        date = release_date,
        rating = vote_average,
        tagline = tagline,
        genres = genres?.joinToString { it.name },
        runtime = runtime,
        status = status,
        seasonsCount = null, // movies don't have seasons
        homepage = homepage,
        productionCompanies = production_companies?.joinToString { it.name }
    )
}

private fun TvDetails.toUiMedia(): UiMedia {
    return UiMedia(
        id = id ?: 0,
        title = name ?: "",
        posterPath = poster_path,
        backdropPath = backdrop_path,
        overview = overview,
        date = first_air_date,
        rating = vote_average,
        tagline = tagline,
        genres = genres?.joinToString { it.name },
        runtime = episode_run_time?.firstOrNull(),
        status = status,
        seasonsCount = number_of_seasons,
        homepage = homepage,
        productionCompanies = production_companies?.joinToString { it.name }
    )
}

@Composable
fun DetailsScreen(
    viewMode: DetailsViewModel,
    id: Int,
    mediaType: String,
    events: (DetailsEvents) -> Unit,
    onclick:() -> Unit
) {

    LaunchedEffect(key1 = id, key2 = mediaType) {
        events(DetailsEvents.getIdMediaType(id, mediaType))
    }
    val uiState = if (mediaType.equals("movie", ignoreCase = true)) {
        viewMode.detailsMovie.collectAsState().value
    } else {
        viewMode.detailsTv.collectAsState().value
    }

    val isFavorite = viewMode.isFavorite.collectAsState().value

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }
        }

        uiState.items != null -> {
            val data = if (mediaType.equals("movie", ignoreCase = true)) {
                (uiState.items as MovieDetails).toUiMedia()   // MovieDetails → UiMedia
            } else {
                (uiState.items as TvDetails).toUiMedia()   // TvDetails → UiMedia
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp) // if you want spacing for bottom nav
            ) {
                item {
                    CollapsingPosterScreen(
                        title = data.title,
                        isFavorite = isFavorite,
                        posterUrl = data.backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
                        onFavClicked = {
                            events(DetailsEvents.toggleFavorite(isFav = it, data = data))
                        }
                    ) {
                        Text(text = data.overview ?: "No overview", modifier = Modifier.padding(16.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Info Row as single item
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = "Rating: ${data.rating}", modifier = Modifier.weight(1f))
                        Text(text = "Genres: ${data.genres}", modifier = Modifier.weight(1f))
                        Text(text = "Release: ${data.date}", modifier = Modifier.weight(1f))
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                onclick()
                            },
                            modifier = Modifier.size(120.dp, 40.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text("Watch Trailer")
                        }
                    }
                }
            }

        }
    }
}








