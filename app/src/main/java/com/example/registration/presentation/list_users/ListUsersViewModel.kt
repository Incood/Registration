package com.example.registration.presentation.list_users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.EventHandler
import com.example.registration.data.UserDao
import com.example.registration.presentation.list_users.view_models.ListUsersEvent
import com.example.registration.presentation.list_users.view_models.ListUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(private val userDao: UserDao) :
    ViewModel(), EventHandler<ListUsersEvent> {
    val state: MutableStateFlow<ListUsersState.Content>
        get() = _state
    private val _state = MutableStateFlow(ListUsersState.Content())
    override fun obtainEvent(event: ListUsersEvent) {
        when (event) {
            is ListUsersEvent.ListUsers -> fetchUsers()
            is ListUsersEvent.DeleteUser -> deleteUser(event.userId)
        }
    }


    private fun fetchUsers() {
        viewModelScope.launch {
            val users = userDao.getAllUsers()
            _state.value = ListUsersState.Content(users = users, currentUser = _state.value.currentUser)
            Log.e("ViewModel", "2")
        }
    }

    private fun deleteUser(userId: Long) {
        viewModelScope.launch {
            userDao.deleteUser(userId)
            fetchUsers()
        }
    }
}
