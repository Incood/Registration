package com.example.registration.presentation.list_users

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.registration.presentation.list_users.view_models.ListUsersState
import com.example.registration.presentation.list_users.view_state.ListUsersDisplay

@Composable
fun ListUsersScreen(viewModel: ListUsersViewModel, navHostController: NavHostController) {
    val viewState = viewModel.state.collectAsState()

    when (val state = viewState.value) {
        is ListUsersState.Content -> {
            ListUsersDisplay(
                state = state,
                navHostController = navHostController,
                onEvent = { viewModel.obtainEvent(it) }
            )
        }
    }
}