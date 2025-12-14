package com.example.fleekpeek.presentations.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.remote.model.TMDBItem

@Composable
fun CarouselSection(title: String, state: SectionUiState, onItemClick: (Int, String) -> Unit, onviewAllClicked: (List<TMDBItem>) -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { /* View More */ }) {
                Text("View more",Modifier.clickable {
                    onviewAllClicked(state.items ?: emptyList())
                })
            }
        }
        if (state.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error)
            }
        } else {
            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(state.items) { item ->
                    com.example.fleekpeek.presentations.common.CarouselItem(item = item, onClick = { onItemClick(item.id ?: 0, item.media_type ?: "movie") })
                }
            }
        }
    }
}