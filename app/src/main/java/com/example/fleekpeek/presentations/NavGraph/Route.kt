package com.example.fleekpeek.presentations.NavGraph

sealed class Route(val route: String) {

    object onIntroScreen: Route(route = "onIntroScreen")
    object HomeScreen: Route(route = "homeScreen")
    object SearchScreen: Route(route = "SearchScreen")
    object MoviesScreen: Route(route = "MoviesScreen")
    object TvScreen: Route(route = "TvScreen")
    object FavouritesScreen: Route(route = "FavoriteScreen")
    object DetailsScreen:Route(route = "DetailsScreen")
    object AppStartNavigation: Route(route = "AppStartNavigation")
    object FleekPeekNavigation: Route(route = "FleekPeekNavigation")
    object FleekPeekNavigationScreen: Route(route = "FleekPeekNavigationScreen")
    object PlayerScreen: Route(route = "playerScreen")
    object ViewallScreen: Route(route = "viewallscreen")

}