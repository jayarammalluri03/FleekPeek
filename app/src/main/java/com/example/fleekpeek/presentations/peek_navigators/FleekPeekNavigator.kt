package com.example.fleekpeek.presentations.peek_navigators

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fleekpeek.R
import com.example.fleekpeek.presentation.home.HomeViewModel
import com.example.fleekpeek.presentations.NavGraph.Route
import com.example.fleekpeek.presentations.ui.player.PlayerScreen
import com.example.fleekpeek.presentations.ui.details.DetailsScreen
import com.example.fleekpeek.presentations.ui.favoriteScreen.FavoritesScreen
import com.example.fleekpeek.presentations.ui.home.HomeScreen
import com.example.fleekpeek.presentations.ui.popup.SettingScreen
import com.example.fleekpeek.presentations.ui.search.SearchScreen
import com.example.fleekpeek.presentations.ui.tvmovie.TvMovieScreen
import com.example.fleekpeek.presentations.ui.viewall.ViewAllScreen
import com.example.fleekpeek.presentations.ui.viewall.ViewAllViewModel
import com.example.fleekpeek.presentations.viewModels.DetailsViewModel
import com.example.fleekpeek.presentations.viewModels.FavoritesViewModel
import com.example.fleekpeek.presentations.viewModels.SearchScreenViewModel
import com.example.fleekpeek.presentations.viewModels.SettingsViewModel
import com.example.fleekpeek.presentations.viewModels.TVMovieViewmodel
import com.example.fleekpeek.Data.local.remote.model.TMDBItem


@Composable
fun FleekPeekNavigator(parentNavController: NavController) {

    val navController = rememberNavController()

    val bottomNavItems = remember {
        listOf(
            BottomNavItem(icon = R.drawable.baseline_home_24, text = "Home"),
            BottomNavItem(icon = R.drawable.baseline_search_24, text = "Search"),
            BottomNavItem(icon = R.drawable.baseline_tv_24, text = "Tv"),
            BottomNavItem(icon = R.drawable.baseline_fav_24, text = "Favorites")
        )
    }

    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route


    var selectedIndex = remember(key1 = backStackEntry) {

        val effectiveRoute = if (currentRoute == Route.DetailsScreen.route)
            navController.previousBackStackEntry?.destination?.route ?: currentRoute
        else
            currentRoute



        when (effectiveRoute) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.TvScreen.route -> 2
            Route.FavouritesScreen.route -> 3
            else -> 0
        }
    }

    val showBars = currentRoute != Route.PlayerScreen.route

    var showSignOutSheet by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showBars) FleekPeekTopBar(navController, onSettingsClicked = {
                showSignOutSheet = true
            })
        },
        bottomBar = {
            if (!showBars) {
            } else
                BottomNavigation(
                    item = bottomNavItems,
                    selected = selectedIndex,
                    onItemClicked = {
                        when (it) {
                            0 -> navigateToTop(navController, Route.HomeScreen.route)
                            1 -> navigateToTop(navController, Route.SearchScreen.route)
                            2 -> navigateToTop(navController, Route.TvScreen.route)
                            3 -> navigateToTop(navController, Route.FavouritesScreen.route)
                        }
                    })
        }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel, navigateToDetails = { id, mediatype ->
                        navigateToDetails(
                            navController = navController,
                            id = id,
                            mediaType = mediatype ?: ""
                        )
                    },
                    navigateToViewAll = {
                        navigateToViewAll(navController, it)
                    })
            }
            composable(Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val prevEntry = navController.previousBackStackEntry
                val id = remember { prevEntry?.savedStateHandle?.get<Int?>("id") }
                val mediaType = remember { prevEntry?.savedStateHandle?.get<String?>("mediatype") }
                DetailsScreen(
                    viewModel,
                    events = viewModel::onEvent,
                    id = id ?: 0,
                    mediaType = mediaType ?: "",
                    onclick = {
                        navigateToPlayerScreen(navController)
                    })
            }

            composable(Route.PlayerScreen.route) {
                PlayerScreen()
            }

            composable(Route.ViewallScreen.route) {
                val viewModel: ViewAllViewModel = hiltViewModel()
                val prevEntry = navController.previousBackStackEntry
                val list =
                    remember { prevEntry?.savedStateHandle?.get<List<TMDBItem>?>("list") }
                ViewAllScreen(
                    items = list ?: emptyList(),
                    events = viewModel::onEvent,
                    viewModel = viewModel,
                    onclick = { id, mediatype ->
                        navigateToDetails(
                            navController = navController,
                            id = id,
                            mediaType = mediatype ?: ""
                        )
                    })
            }

            composable(Route.SearchScreen.route) {
                val viewModel: SearchScreenViewModel = hiltViewModel()
                SearchScreen(viewModel, navigateToDetail = { id, mediatype ->
                    navigateToDetails(
                        navController = navController,
                        id = id,
                        mediaType = mediatype ?: ""
                    )
                })
            }
            composable(Route.TvScreen.route) {
                val viewModel: TVMovieViewmodel = hiltViewModel()
                TvMovieScreen(
                    viewModel,
                    isTv = true,
                    events = viewModel::onEvent,
                    navigateToDetails = { id, mediatype ->
                        navigateToDetails(
                            navController = navController,
                            id = id,
                            mediaType = mediatype ?: ""
                        )
                    })
            }
            composable(Route.MoviesScreen.route) {

            }
            composable(Route.FavouritesScreen.route) {
                val viewModel: FavoritesViewModel = hiltViewModel()
                FavoritesScreen(viewModel, navigateToDetails = { id, mediatype ->
                    navigateToDetails(
                        navController = navController,
                        id = id,
                        mediaType = mediatype ?: ""
                    )
                })
            }
        }
    }
    if (showSignOutSheet) {
        val viewModel= hiltViewModel<SettingsViewModel>()
        SettingScreen (
            viewModel,
            onDismiss = { showSignOutSheet = false },
            onSignOut = {
              //  FirebaseAuth.getInstance().signOut()
                showSignOutSheet = false
                parentNavController.navigate(Route.SigninNavigation.route) {
                    popUpTo(Route.FleekPeekNavigation.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}


private fun navigateToTop(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToPlayerScreen(navController: NavController) {
    navController.navigate(route = Route.PlayerScreen.route)
}


private fun navigateToDetails(navController: NavController, id: Int, mediaType: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
    navController.currentBackStackEntry?.savedStateHandle?.set("mediatype", mediaType)
    navController.navigate(route = Route.DetailsScreen.route)
}

private fun navigateToViewAll(navController: NavController, list: List<TMDBItem>) {
    navController.currentBackStackEntry?.savedStateHandle?.set("list", list)
    navController.navigate(route = Route.ViewallScreen.route)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FleekPeekTopBar(
    navController: NavHostController,
    onSettingsClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text("FleekPeek")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navigateToTop(navController, Route.SearchScreen.route)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fleekpeek_logo),
                    contentDescription = "Search",
                    tint = Color.Unspecified
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onSettingsClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}
