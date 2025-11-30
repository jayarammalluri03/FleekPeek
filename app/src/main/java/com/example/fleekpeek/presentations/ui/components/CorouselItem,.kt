package com.example.fleekpeek.presentations.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fleekpeek.remote.model.TMDBItem

@Composable
fun CarouselItem(item: TMDBItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(140.dp).clickable {
                onClick()
            }
    ) {
        val poster = item.displayPoster()
        val imageUrl = poster?.let { "https://image.tmdb.org/t/p/w342$it" }

        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = item.displayTitle(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No Image")
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = item.displayTitle(),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )
    }
}