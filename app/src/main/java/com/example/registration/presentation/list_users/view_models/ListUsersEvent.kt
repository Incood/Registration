package com.example.registration.presentation.list_users.view_models

sealed class ListUsersEvent {

    data object ListUsers : ListUsersEvent()
    data class DeleteUser(val userId: Long) : ListUsersEvent()
    data class SaveToken(val isAuthorized: Boolean) : ListUsersEvent()
}