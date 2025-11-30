package com.example.fleekpeek.presentations.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.fleekpeek.Dimen
import com.example.fleekpeek.R
import com.example.fleekpeek.presentations.ui.IntroPage

@Composable
fun OnIntroPage(modifier: Modifier,page: IntroPage){

    Column(modifier = Modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimen.MediumPadding1))

        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = Dimen.MediumPadding1),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.body)
        )
        Spacer(modifier = Modifier.height(Dimen.MediumPadding1))
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = Dimen.MediumPadding1),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_medium)
        )


    }

}