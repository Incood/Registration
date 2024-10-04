package com.example.registration.presentation.auth.authorization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.registration.presentation.auth.authorization.view_models.AuthorizationState
import com.example.registration.presentation.auth.authorization.view_state.AuthorizationDisplay

@Composable
fun AuthorizationScreen(viewModel : AuthorizationViewModel, navHostController: NavHostController) {

    val viewState = viewModel.state.collectAsState()

    when (val state = viewState.value){
        is AuthorizationState.Content -> AuthorizationDisplay(state = state,navHostController = navHostController , onEvent =  {
            viewModel.obtainEvent(it)
        })
    }
}