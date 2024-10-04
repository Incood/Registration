package com.example.registration.presentation.list_users.view_models

import com.example.registration.data.User

sealed class ListUsersState {
    data class Content(
        val users: List<User> = emptyList(),
        val currentUser: User? = null
    )
}