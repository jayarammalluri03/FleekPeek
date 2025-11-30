package com.example.fleekpeek.presentations.ui

import androidx.annotation.DrawableRes
import com.example.fleekpeek.R

data class IntroPage(val title: String, val description: String, @DrawableRes val image: Int)

val pages = listOf(
    IntroPage(
        title = "Discover Trending Entertainment",
        description = "Stay updated with the latest movies, shows, and trailers curated just for you on Fleek Peek",
        image = (R.drawable.poster1)
    ),
    IntroPage(
        title = "Personalized Recommendations",
        description = "Get smart suggestions based on your watch history and preferences for a better Experience",
        image = R.drawable.varansi
    ),
    IntroPage(
        title = "Watch Anytime, Anywhere",
        description = "Enjoy seamless streaming with a clean UI, fast performance, and smooth playback across all your devices.",
        image = R.drawable.poster3
    )
)
