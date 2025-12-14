package com.example.fleekpeek.presentations.ui.tvmovie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.presentations.ui.components.CarouselSection
import com.example.fleekpeek.presentations.viewModels.TVMovieViewmodel

@Composable
fun TvMovieScreen(
    viewModel: TVMovieViewmodel,
    isTv: Boolean,
    events: (TVMovieEvent) -> Unit,
    navigateToDetails: (Int, String) -> Unit
) {


    viewModel.onEvent(TVMovieEvent.loadData(isTv))

    val sciFiState by viewModel.sciFi.collectAsState()
    val bollywoodActionState by viewModel.bollywoodAction.collectAsState()
    val crimeMysteryState by viewModel.crimeMystery.collectAsState()
    val hiddenGemsState by viewModel.hideenGemsMovies.collectAsState()
    val darkThrillerTvState by viewModel.darkThrillerTv.collectAsState()
    val animeTvState by viewModel.animeTvShows.collectAsState()
    val comfortTvState by viewModel.comfortTvShows.collectAsState()
    val kDramaTvState by viewModel.kDramaTvShows.collectAsState()


    val tvSectionList = listOf(darkThrillerTvState, animeTvState, comfortTvState, kDramaTvState)

    val movieSectionList =
        listOf(sciFiState, bollywoodActionState, crimeMysteryState, hiddenGemsState)

    val isAnyTvShowLoading = tvSectionList.any {
        it.isLoading
    }

    val isAnyMovieLoading = movieSectionList.any {
        it.isLoading
    }

    val firsttvError = tvSectionList.firstOrNull { it.error != null }?.error

    val firstMovieError = movieSectionList.firstOrNull { it.error != null }?.error


    when {
        isTv -> {
            when {
                isAnyTvShowLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                firsttvError != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = firsttvError.toString())
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(tvSectionList.size) {
                            if (tvSectionList[it].items.isNotEmpty()) {
                                val header = when (it) {
                                    0 -> "Dark Thrillers"
                                    1 -> "Anime Shows"
                                    2 -> "Comfort TV"
                                    3 -> "K Drama"
                                    else -> ""
                                }
                                CarouselSection(
                                    title = header,
                                    state = tvSectionList[it],
                                    onItemClick = { id, mediaType ->
                                        navigateToDetails(id, if (isTv) "tv" else "movie")
                                    },
                                    onviewAllClicked = {
                                    }
                                )
                            }
                        }
                    }
                }

            }

        }

        else -> {
            when {
                isAnyMovieLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                firstMovieError != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = firsttvError.toString())
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(movieSectionList.size) { index ->

                            val header = when (index) {
                                0 -> "Sci Fi"
                                1 -> "Bollywood Action"
                                2 -> "Crime Mystery"
                                3 -> "Hidden Gems"
                                else -> ""
                            }

                            CarouselSection(
                                title = header,
                                state = movieSectionList[index],
                                onItemClick = { id, _ ->
                                    navigateToDetails(id, "movie")
                                },
                                onviewAllClicked = {
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}
