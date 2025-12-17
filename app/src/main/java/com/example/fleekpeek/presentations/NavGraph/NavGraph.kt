package com.example.fleekpeek.presentations.NavGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.fleekpeek.presentations.peek_navigators.FleekPeekNavigator
import com.example.fleekpeek.presentations.ui.auth.SignUpScreen
import com.example.fleekpeek.presentations.ui.components.OnIntroScreen
import com.example.fleekpeek.presentations.ui.login.SignInScreen
import com.example.fleekpeek.presentations.viewModels.IntroPageViewModel
import com.example.fleekpeek.presentations.viewModels.LoginViewModel
import com.example.fleekpeek.presentations.viewModels.SignUpViewModel


@Composable
fun NavGraph(
    startDestination: String
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {


        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.onIntroScreen.route
        ) {
            composable(
                route = Route.onIntroScreen.route
            ) {
                val viewModel: IntroPageViewModel = hiltViewModel()
                OnIntroScreen(events = viewModel::onEvent, navController = navController)
            }
        }

        navigation(
            route = Route.SigninNavigation.route,
            startDestination = Route.SignInScreen.route
        ) {
            composable(
                route = Route.SignInScreen.route
            ) {
                val viewModel = hiltViewModel<LoginViewModel>()
                SignInScreen(viewModel,onSignUpClick = {
                    navController.navigate(Route.SignUpScreen.route)
                },
                    onLoginClicked = {
                        navController.navigate(Route.FleekPeekNavigationScreen.route)
                    })
             }

            composable(
                route = Route.SignUpScreen.route
            ) {
                val viewModel = hiltViewModel<SignUpViewModel>()
                SignUpScreen(viewModel, succesSignUp= {
                    navController.navigate(Route.FleekPeekNavigationScreen.route)
                })
             }
        }




        navigation(
            route = Route.FleekPeekNavigation.route,
            startDestination = Route.FleekPeekNavigationScreen.route
        ) {

            composable(route = Route.FleekPeekNavigationScreen.route) {
                FleekPeekNavigator(navController)
            }
        }

    }


}