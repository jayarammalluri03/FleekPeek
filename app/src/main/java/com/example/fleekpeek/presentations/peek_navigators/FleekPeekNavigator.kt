    package com.example.fleekpeek.presentations.peek_navigators

    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Modifier
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
    import com.example.fleekpeek.presentations.ui.details.DetailsScreen
    import com.example.fleekpeek.presentations.ui.favoriteScreen.FavoritesScreen
    import com.example.fleekpeek.presentations.ui.home.HomeScreen
    import com.example.fleekpeek.presentations.ui.search.SearchScreen
    import com.example.fleekpeek.presentations.ui.tvmovie.TvMovieScreen
    import com.example.fleekpeek.presentations.viewModels.DetailsViewModel
    import com.example.fleekpeek.presentations.viewModels.FavoritesViewModel
    import com.example.fleekpeek.presentations.viewModels.SearchScreenViewModel
    import com.example.fleekpeek.presentations.viewModels.TVMovieViewmodel


    @Composable
    fun FleekPeekNavigator() {

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

        var selectedIndex= remember(key1 = backStackEntry) {
            val currentRoute = backStackEntry?.destination?.route

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


            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    FleekPeekTopBar(navController)
                },
                bottomBar = {
                   BottomNavigation(item = bottomNavItems,
                       selected = selectedIndex,
                       onItemClicked = {
                           when (it) {
                               0 -> navigateToTop(navController, Route.HomeScreen.route)
                               1-> navigateToTop(navController, Route.SearchScreen.route)
                               2 -> navigateToTop(navController, Route.TvScreen.route)
                               3 -> navigateToTop(navController, Route.FavouritesScreen.route)
                           }
                       })
                }) {
                val bottomPadding = it.calculateBottomPadding()
                NavHost(navController= navController,
                    startDestination = Route.HomeScreen.route,
                    modifier = Modifier.padding(it)){
                    composable(Route.HomeScreen.route){
                        val viewModel: HomeViewModel= hiltViewModel()
                       HomeScreen(viewModel, navigateToDetails = { id, mediatype ->
                               navigateToDetails(navController= navController, id = id, mediaType = mediatype ?: "")
                       })
                    }
                    composable(Route.DetailsScreen.route) {
                        val viewModel: DetailsViewModel = hiltViewModel()
                        val prevEntry = navController.previousBackStackEntry
                        val id = remember { prevEntry?.savedStateHandle?.get<Int?>("id") }
                        val mediaType = remember { prevEntry?.savedStateHandle?.get<String?>("mediatype") }
                        DetailsScreen(viewModel, events = viewModel::onEvent, id = id ?: 0, mediaType = mediaType ?: "")

                    }


                    composable(Route.SearchScreen.route){
                        val viewModel: SearchScreenViewModel = hiltViewModel()
                        SearchScreen(viewModel, navigateToDetail = { id, mediatype ->
                            navigateToDetails(navController= navController, id = id, mediaType = mediatype ?: "")
                        })
                    }
                    composable(Route.TvScreen.route){
                      val viewModel: TVMovieViewmodel= hiltViewModel()
                        TvMovieScreen(viewModel,isTv = true, events = viewModel::onEvent, navigateToDetails = { id, mediatype ->
                            navigateToDetails(navController= navController, id = id, mediaType = mediatype ?: "")
                        })
                    }
                    composable(Route.MoviesScreen.route){
                        /*val viewModel: TVMovieViewmodel= hiltViewModel()
                        TvMovieScreen(viewModel,isTv = false, events = viewModel::onEvent, navigateToDetails = { id, mediatype ->
                            navigateToDetails(navController= navController, id = id, mediaType = mediatype ?: "")
                        })*/
                    }
                    composable(Route.FavouritesScreen.route){
                     val viewModel: FavoritesViewModel= hiltViewModel()
                        FavoritesScreen(viewModel, navigateToDetails = { id, mediatype ->
                            navigateToDetails(navController= navController, id = id, mediaType = mediatype ?: "")
                        })
                    }
            }
        }
    }


    private fun navigateToTop(navController: NavController, route: String){
        navController.navigate(route){
            navController.graph.startDestinationRoute?.let {
                popUpTo(it){
                    saveState = true
                }
                restoreState= true
                launchSingleTop= true
            }
        }
    }


    private fun navigateToDetails(navController: NavController, id: Int,mediaType: String){
        navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
        navController.currentBackStackEntry?.savedStateHandle?.set("mediatype", mediaType)
        navController.navigate(route = Route.DetailsScreen.route)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FleekPeekTopBar(
        navController: NavHostController
    ) {
        TopAppBar(
            title = {
                Text("FleekPeek")
            },
            navigationIcon = {
                IconButton(onClick = {
                    navigateToTop(navController, Route.SearchScreen.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.fleekpeek_logo),
                        contentDescription = "Menu",
                        tint = androidx.compose.ui.graphics.Color.Unspecified
                    )
                }
            },
            actions = {
            }
        )
    }