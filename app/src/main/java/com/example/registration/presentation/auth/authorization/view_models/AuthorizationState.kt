package com.example.registration.presentation.auth.authorization.view_models

sealed class AuthorizationState {
    data class Content(
        val userName: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val errorUserName: Boolean = false,
        val errorPassword : Boolean = false,
        val successAuthorization : Boolean = false
    ) : AuthorizationState()
}
