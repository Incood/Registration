package com.example.registration.presentation.list_users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.EventHandler
import com.example.registration.data.UserDao
import com.example.registration.domain.repository.SharedPrefRepository
import com.example.registration.presentation.list_users.view_models.ListUsersEvent
import com.example.registration.presentation.list_users.view_models.ListUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(
    private val userDao: UserDao,
    private val sharedPrefRepository: SharedPrefRepository
) :
    ViewModel(), EventHandler<ListUsersEvent> {
    val state: MutableStateFlow<ListUsersState.Content>
        get() = _state
    private val _state = MutableStateFlow(ListUsersState.Content())

    init {
        viewModelScope.launch {
            val currentUserId = sharedPrefRepository.getCurrentUserId()
            val currentUser = userDao.getUserById(currentUserId)
            _state.value = _state.value.copy(currentUser = currentUser)
            fetchUsers()
        }
    }
    override fun obtainEvent(event: ListUsersEvent) {
        when (event) {
            is ListUsersEvent.ListUsers -> fetchUsers()
            is ListUsersEvent.DeleteUser -> deleteUser(event.userId)
            is ListUsersEvent.SaveToken -> saveToken(event.isAuthorized)
        }
    }


    private fun fetchUsers() {
        viewModelScope.launch {
            val users = userDao.getAllUsers()
            _state.value = ListUsersState.Content(users = users, currentUser = _state.value.currentUser)
        }
    }

    private fun deleteUser(userId: Long) {
        viewModelScope.launch {
            userDao.deleteUser(userId)
            fetchUsers()
        }
    }

    private fun saveToken(authorized: Boolean) {
        viewModelScope.launch {
            sharedPrefRepository.saveToken(authorized)
        }
    }
}
