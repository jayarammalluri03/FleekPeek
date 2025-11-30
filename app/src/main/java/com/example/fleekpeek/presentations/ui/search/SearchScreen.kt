package com.example.fleekpeek.presentations.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.fleekpeek.presentations.common.CarouselItem
import com.example.fleekpeek.presentations.ui.components.SearchBox
import com.example.fleekpeek.presentations.viewModels.SearchScreenViewModel

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel,
    navigateToDetail: (Int, String) -> Unit
) {
    val state by viewModel.state
    val pagingItem= viewModel.searchResults.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        SearchBox(
            query = state.searchQuery,
            onQueryChange = { viewModel.onEvent(SearchEvent.updateSeachQuery(it)) },
            onSearch = {
                viewModel.onEvent(SearchEvent.searchitems)
            }
        )


        Spacer(modifier = Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pagingItem.itemCount) {
                pagingItem?.get(it)?.let { item ->
                    CarouselItem(item = item, onClick = { navigateToDetail(item.id ?: 0, " tv") })
                }
            }


            pagingItem.apply {
                when {
                    state.searchQuery.isEmpty() -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center) {
                                Text(text = "Please enter your text")
                            }
                        }
                    }
                    loadState.refresh is LoadState.Loading -> {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }){
                            CenteredLoading()
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        // next page
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            BottomLoading()
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = loadState.refresh as LoadState.Error
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ErrorItem(
                                message = e.error.localizedMessage ?: "Something went wrong",
                                onRetry = { retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ErrorItem(
                                message = e.error.localizedMessage ?: "Something went wrong",
                                onRetry = { retry() }
                            )
                        }
                    }
                }
            }

        }
    }


}


@Composable
fun CenteredLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun BottomLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 2.dp)
    }
}


@Composable
fun ErrorItem(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Retry")
        }
    }
}

