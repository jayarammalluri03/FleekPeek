package com.example.fleekpeek.presentations.ui.favoriteScreen



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.presentations.viewModels.FavoritesViewModel
import com.example.fleekpeek.Data.local.remote.model.TMDBItem

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navigateToDetails: (Int, String) -> Unit
) {
    val state by viewModel.favoritesState.collectAsState()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error ?: "Something went wrong")
            }
        }

        state.items.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorites yet")
            }
        }

        else -> {
            FavoritesList(
                state = state,
                navigateToDetails = navigateToDetails
            )
        }
    }
}

@Composable
private fun FavoritesList(
    state: SectionUiState,
    navigateToDetails: (Int, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.items) { item ->
            FavoriteItemRow(
                item = item,
                onClick = { navigateToDetails(item.id ?: 0, item.media_type ?: "movie") }
            )
        }
    }
}

@Composable
private fun FavoriteItemRow(
    item: TMDBItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 6.dp)
    ) {
        val imageUrl = item.poster_path?.let { "https://image.tmdb.org/t/p/w780$it" }
        AsyncImage(

            model = imageUrl,
            contentDescription = item.title,
            modifier = Modifier
                .width(80.dp)
                .height(120.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.overview ?: "",
                maxLines = 3,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.media_type ?: "", // MOVIE / TV
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
