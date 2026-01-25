package com.example.fleekpeek.presentations.ui.viewall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fleekpeek.presentations.common.CarouselItem
import com.example.fleekpeek.Data.local.remote.model.TMDBItem

@Composable
fun ViewAllScreen(
    items: List<TMDBItem>,
    viewModel: ViewAllViewModel,
    events: (ViewAllEvent) -> Unit,
    onclick: (Int, String) -> Unit
) {
    viewModel.onEvent(ViewAllEvent.getViewMoreData(items))
    val data = viewModel.viewAllData.collectAsState().value

    when{
        data.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        data.items != null ->{
            Box() {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data.items?.size ?: 0) {
                        CarouselItem(item = data.items?.get(it) ?: TMDBItem(), onClick = { onclick(data.items?.get(it)?.id ?: 0, " tv") })
                    }
                }
            }
        }
    }
}