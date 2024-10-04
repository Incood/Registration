package com.example.registration.presentation.auth.registration.view_models

sealed class RegistrationState {
    data class Content(
        val userName: String = "",
        val password: String = "",
        val dateOfBirth: String = "",
        val isLoading: Boolean = false,
        val errorUsername : Boolean = false,
        val errorPassword : Boolean = false,
        val successRegistration : Boolean = false
    ) : RegistrationState()
}
