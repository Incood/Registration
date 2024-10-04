package com.example.registration.presentation.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.EventHandler
import com.example.registration.data.User
import com.example.registration.data.UserDao
import com.example.registration.presentation.auth.registration.view_models.RegistrationEvent
import com.example.registration.presentation.auth.registration.view_models.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userDao: UserDao) :
    ViewModel(), EventHandler<RegistrationEvent> {
    val state : StateFlow<RegistrationState>
        get() = _state
    private val _state = MutableStateFlow(RegistrationState.Content())
    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.SignUp -> with(_state.value) {
                if (
                    userName.isNotEmpty() &&
                    password.isNotEmpty() &&
                    dateOfBirth.isNotEmpty()
                    )
                    saveUser(
                        name = userName,
                        password = password,
                        dateOfBirth = dateOfBirth,
                        avatarUri = null

                ) else _state.update { currentState ->
                    currentState.copy(
                        errorUsername = userName.isEmpty(),
                        errorPassword = password.isEmpty()
                    )
                }
            }
            is RegistrationEvent.ChangeUserName -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        userName = userNameTextFieldValue,
                        errorUsername = false,
                        errorPassword = false
                    )
                }
            }
            is RegistrationEvent.ChangePassword -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        password = passwordTextFieldValue,
                        errorUsername = false,
                        errorPassword = false
                    )
                }
            }
            is RegistrationEvent.ChangeDate -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        dateOfBirth = dateTextFieldValue,
                        errorUsername = false,
                        errorPassword = false
                    )
                }
            }
            is RegistrationEvent.ResetAuthorization -> {
                _state.update { currentState ->
                    currentState.copy(successRegistration = false)
                }
            }
            else -> {}
        }
    }

    private fun saveUser(name: String, password: String, dateOfBirth: String, avatarUri: String?) {
        val user = User(id = 0L, name = name, password = password, dateOfBirth = dateOfBirth, avatarUri = avatarUri)
        viewModelScope.launch {
            try {
                userDao.insertUser(user)
                _state.update { currentState ->
                    currentState.copy(successRegistration = true)
                }
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(successRegistration = false)
                }
            }
        }
    }
}
