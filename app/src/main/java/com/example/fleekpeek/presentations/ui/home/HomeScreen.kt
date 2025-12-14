package com.example.fleekpeek.presentations.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.presentation.home.HomeViewModel
import com.example.fleekpeek.presentations.ui.components.CarouselSection
import com.example.fleekpeek.remote.model.TMDBItem
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(viewModel: HomeViewModel, navigateToDetails: (Int, String?) -> Unit, navigateToViewAll: (List<TMDBItem>) -> Unit) {

    val trendingState by viewModel.trending.collectAsState()
    val popularMoviesState by viewModel.popularMovies.collectAsState()
    val popularTvState by viewModel.popularTv.collectAsState()
    val upcomingState by viewModel.upcoming.collectAsState()

    val sectionStates = listOf(trendingState, popularMoviesState, popularTvState, upcomingState)

    val isAnyLoading = sectionStates.any { it.isLoading }

    val firstError = sectionStates.firstOrNull { it.error != null }?.error


    when {
        isAnyLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        firstError != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = firstError)
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    TrendingBannerSection(
                        title = "Trending",
                        state = trendingState,
                        onItemClick = { id, mediaType ->
                            navigateToDetails(id, "movie")
                        }
                    )
                }

                item {
                    CarouselSection(
                        title = "Popular Movies",
                        state = popularMoviesState,
                        onItemClick = { id, mediaType ->
                            navigateToDetails(id, "movie")
                        },
                        onviewAllClicked = {
                            navigateToViewAll(it)
                        }
                    )
                }

                item {
                    CarouselSection(
                        title = "Popular TV",
                        state = popularTvState,
                        onItemClick = { id, mediaType ->
                            navigateToDetails(id, "tv")
                        },
                        onviewAllClicked = {
                            navigateToViewAll(it)
                        }
                    )
                }

                item {
                    CarouselSection(
                        title = "Upcoming",
                        state = upcomingState,
                        onItemClick = { id, mediaType ->
                            navigateToDetails(id, "movie")
                        },
                        onviewAllClicked = {
                            navigateToViewAll(it)
                        }
                    )
                }
            }
        }

    }
}






@Composable
fun BannerItem(
    item: TMDBItem,
    onClick: () -> Unit
) {
    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2)
            .padding(horizontal = 16.dp)
    ) {
        val poster = item.displayPoster()
        val imageUrl = poster?.let { "https://image.tmdb.org/t/p/w780$it" }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick() }
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = item.displayTitle(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Image")
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendingBannerSection(
    title: String,
    state: SectionUiState,
    onItemClick: (Int, String?) -> Unit
) {
    val items = state.items?.take(5).orEmpty()

    if (items.isEmpty()) return
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )

    // Optional: auto-scroll every 3 seconds
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    val currentIndex = pagerState.currentPage % items.size
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            val realIndex = page % items.size
            val item = items[realIndex]

            BannerItem(
                item = item,
                onClick = { onItemClick(item.id ?: 0, item.media_type) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dots indicator
        DotsIndicator(
            totalDots = items.size,
            selectedIndex = currentIndex,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex) selectedColor else unSelectedColor
            val size = if (index == selectedIndex) 10.dp else 8.dp

            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}




