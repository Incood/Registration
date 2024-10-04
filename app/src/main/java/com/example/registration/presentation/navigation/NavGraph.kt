package com.example.registration.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.registration.presentation.auth.authorization.AuthorizationScreen
import com.example.registration.presentation.auth.authorization.AuthorizationViewModel
import com.example.registration.presentation.auth.no_authorizated.NoAuthScreen
import com.example.registration.presentation.auth.registration.RegistrationScreen
import com.example.registration.presentation.auth.registration.RegistrationViewModel
import com.example.registration.presentation.list_users.ListUsersScreen
import com.example.registration.presentation.list_users.ListUsersViewModel

@Composable
fun NavGraph(navController: NavHostController, padding: PaddingValues) {

    NavHost(
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) },
        popEnterTransition = { fadeIn(animationSpec = tween(200)) },
        popExitTransition = { fadeOut(animationSpec = tween(200)) },
        navController = navController,
        startDestination = Screens.NoAuthorization.route,
        modifier = Modifier.padding(paddingValues = padding)
    ) {
        composable(Screens.NoAuthorization.route) {
            NoAuthScreen(navHostController = navController)
        }
        composable(Screens.Authorization.route) {
            val viewModel: AuthorizationViewModel = hiltViewModel()
            AuthorizationScreen(viewModel = viewModel, navHostController = navController)
        }
        composable(Screens.Registration.route){
            val viewModel : RegistrationViewModel = hiltViewModel()
            RegistrationScreen(viewModel = viewModel, navHostController = navController)
        }

        composable(Screens.ListUsers.route) {
            val viewModel: ListUsersViewModel = hiltViewModel()
            ListUsersScreen(viewModel = viewModel, navHostController = navController)
        }
    }
}