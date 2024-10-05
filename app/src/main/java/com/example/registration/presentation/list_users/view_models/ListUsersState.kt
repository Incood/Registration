package com.example.registration.presentation.list_users.view_models

import com.example.registration.data.models.User

sealed class ListUsersState {
    data class Content(
        val users: List<User> = emptyList(),
        val currentUser: User? = null
    )
}