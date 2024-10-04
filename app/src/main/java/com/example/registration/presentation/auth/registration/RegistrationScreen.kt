package com.example.registration.presentation.auth.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.registration.presentation.auth.registration.view_state.RegistrationDisplay
import com.example.registration.presentation.auth.registration.view_models.RegistrationState

@Composable fun RegistrationScreen(viewModel : RegistrationViewModel, navHostController: NavHostController){

    val viewState = viewModel.state.collectAsState()

    when (val state = viewState.value){
        is RegistrationState.Content -> RegistrationDisplay(state = state, navHostController = navHostController, event = {
            viewModel.obtainEvent(it)} )
    }
}