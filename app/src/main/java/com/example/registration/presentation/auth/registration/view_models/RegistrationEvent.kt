package com.example.registration.presentation.auth.registration.view_models

sealed class RegistrationEvent {
    data object SignUp : RegistrationEvent()
    data class ChangeUserName(val userNameTextFieldValue : String) : RegistrationEvent()
    data class ChangePassword(val passwordTextFieldValue : String) : RegistrationEvent()
    data class ChangeDate(val dateTextFieldValue : String) : RegistrationEvent()
    data class ChangeAvatarUri(val uri: String) : RegistrationEvent()
    data object ResetAuthorization : RegistrationEvent()
}