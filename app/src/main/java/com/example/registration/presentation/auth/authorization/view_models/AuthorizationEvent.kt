package com.example.registration.presentation.auth.authorization.view_models

sealed class AuthorizationEvent{
    data object Login : AuthorizationEvent()
    data class ChangeUserName(val userNameTextFieldValue : String) : AuthorizationEvent()
    data class ChangePassword(val passwordTextFieldValue : String) : AuthorizationEvent()
    data object ResetAuthorization : AuthorizationEvent()
}
